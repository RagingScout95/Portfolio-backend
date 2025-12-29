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

echo [1/5] Building JAR with Maven...
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
echo [2/5] Checking if JAR exists...
if not exist "%LOCAL_JAR%" (
    echo.
    echo [ERROR] JAR not found at %LOCAL_JAR%
    echo [ERROR] Build may have failed
    pause
    exit /b 1
)

echo [OK] Build complete: %LOCAL_JAR%
echo.

echo [3/5] Creating remote directory structure...
ssh %REMOTE_HOST% "sudo mkdir -p %REMOTE_RELEASES% && sudo chown $USER:$USER %REMOTE_APP_DIR% -R"
if errorlevel 1 (
    echo.
    echo [ERROR] Failed to create remote directories
    pause
    exit /b 1
)

echo.
echo [4/5] Uploading JAR to server...
scp "%LOCAL_JAR%" "%REMOTE_HOST%:%REMOTE_JAR%"
if errorlevel 1 (
    echo.
    echo [ERROR] Failed to upload JAR
    pause
    exit /b 1
)

echo.
echo [5/5] Switching active version and restarting service...
ssh %REMOTE_HOST% "ln -sfn %REMOTE_JAR% %REMOTE_APP_DIR%/current.jar"
if errorlevel 1 (
    echo.
    echo [ERROR] Failed to create symlink
    pause
    exit /b 1
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

