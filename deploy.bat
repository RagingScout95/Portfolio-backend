@echo off
setlocal enabledelayedexpansion

set APP_NAME=portfolio-backend
set LOCAL_JAR=target\%APP_NAME%-1.0.0.jar

set REMOTE_HOST=ubuntu-server
set REMOTE_APP_DIR=/opt/portfolio-backend
set REMOTE_RELEASES=%REMOTE_APP_DIR%/releases
set SERVICE_NAME=portfolio-backend

REM Generate timestamp (YYYYMMDD_HHMMSS format)
for /f "tokens=2 delims==" %%I in ('wmic os get localdatetime /value') do set datetime=%%I
set TIMESTAMP=%datetime:~0,8%_%datetime:~8,6%
set REMOTE_JAR=%REMOTE_RELEASES%/%APP_NAME%-%TIMESTAMP%.jar

echo.
echo ========================================
echo   PORTFOLIO BACKEND DEPLOYMENT
echo ========================================
echo.

echo [1/6] Building JAR with Maven...
where mvn >nul 2>&1
if errorlevel 1 (
    set MVN_CMD=mvn.cmd
) else (
    set MVN_CMD=mvn
)
call %MVN_CMD% clean package -DskipTests
if errorlevel 1 (
    echo.
    echo [ERROR] Build failed
    pause
    exit /b 1
)

echo.
echo [2/6] Checking if JAR exists...
if not exist "%LOCAL_JAR%" (
    echo.
    echo [ERROR] JAR not found at %LOCAL_JAR%
    echo [ERROR] Build may have failed
    pause
    exit /b 1
)

echo [OK] Build complete: %LOCAL_JAR%
echo.

echo [3/6] Creating remote directory structure...
ssh %REMOTE_HOST% "sudo mkdir -p %REMOTE_RELEASES% && sudo chown $USER:$USER %REMOTE_APP_DIR% -R"
if errorlevel 1 (
    echo.
    echo [ERROR] Failed to create remote directories
    pause
    exit /b 1
)

echo.
echo [4/6] Uploading JAR to server...
scp "%LOCAL_JAR%" "%REMOTE_HOST%:%REMOTE_JAR%"
if errorlevel 1 (
    echo.
    echo [ERROR] Failed to upload JAR
    pause
    exit /b 1
)

echo.
echo [5/6] Switching active version...
ssh %REMOTE_HOST% "ln -sfn %REMOTE_JAR% %REMOTE_APP_DIR%/current.jar"
if errorlevel 1 (
    echo.
    echo [ERROR] Failed to create symlink
    pause
    exit /b 1
)

echo.
echo [6/6] Cleaning up old JAR files...
REM Only deletes files matching portfolio-backend-*.jar in /opt/portfolio-backend/releases directory
REM Safe: Won't affect other projects as it's scoped to this project's directory and filename pattern
ssh %REMOTE_HOST% "cd %REMOTE_RELEASES% && ls -1 %APP_NAME%-*.jar 2>/dev/null | grep -v '%APP_NAME%-%TIMESTAMP%.jar' | xargs -r rm -f"
if errorlevel 1 (
    echo [WARNING] Failed to clean up old JAR files (this is not critical)
) else (
    echo [OK] Old JAR files deleted
)

REM Check if service exists and restart it
ssh %REMOTE_HOST% "sudo systemctl is-enabled %SERVICE_NAME% >/dev/null 2>&1"
if errorlevel 1 (
    echo [WARNING] Service %SERVICE_NAME% not found or not enabled.
    echo [INFO] Symlink created at %REMOTE_APP_DIR%/current.jar
    echo [INFO] Please create and enable the systemd service to auto-start the application.
) else (
    ssh %REMOTE_HOST% "sudo systemctl restart %SERVICE_NAME%"
    if errorlevel 1 (
        echo.
        echo [ERROR] Failed to restart service
        pause
        exit /b 1
    )
    echo [OK] Service restarted successfully
)

echo.
echo ========================================
echo   [SUCCESS] DEPLOYMENT COMPLETE!
echo ========================================
echo.
echo Portfolio Backend updated successfully
echo Timestamp: %TIMESTAMP%
echo.
pause

