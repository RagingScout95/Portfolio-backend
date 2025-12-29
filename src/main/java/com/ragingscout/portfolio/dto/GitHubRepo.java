package com.ragingscout.portfolio.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubRepo {
    private String name;
    private String description;
    private String html_url;
    private String homepage;
    private String language;
    private String[] topics;
}

