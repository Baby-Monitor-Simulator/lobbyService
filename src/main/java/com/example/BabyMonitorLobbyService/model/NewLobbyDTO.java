package com.example.BabyMonitorLobbyService.model;

public class NewLobbyDTO {
        private String scenarioid;

        // Default constructor
        public NewLobbyDTO() {}

        // Constructor
        public NewLobbyDTO(String scenarioid) {
            this.scenarioid = scenarioid;
        }

        // Getter and Setter
        public String getScenarioid() {
            return scenarioid;
        }

        public void setScenarioid(String scenarioid) {
            this.scenarioid = scenarioid;
        }
    }

