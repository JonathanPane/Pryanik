package com.example.pryanik.project.library;

public class StageConfiguration{
    private String title, bean_name, path_to_fxml;
    private boolean resizable, wait_termination;
    private StageConfiguration(){
        title = "Title!";
        bean_name = "BEAN: "+Math.random();
        resizable = true;
        path_to_fxml = null;
        wait_termination = false;
    }
    public static Builder builder(){
        return new Builder();
    }
    public static class Builder{
        private final StageConfiguration res;
        private Builder(){
            res = new StageConfiguration();
        }
        public Builder title(String title){
            res.title = title;
            return this;
        }
        public Builder bean_name(String bean_name){
            res.bean_name = bean_name;
            return this;
        }
        public Builder make_non_resizable(){
            res.resizable = false;
            return this;
        }
        public Builder path_to_fxml(String path_to_fxml){
            res.path_to_fxml = path_to_fxml;
            return this;
        }
        public Builder show_and_wait(){
            res.wait_termination = true;
            return this;
        }
        public StageConfiguration build(){
            return res;
        }
    }

    public String getTitle() {
        return title;
    }

    public String getBean_name() {
        return bean_name;
    }

    public String getPath_to_fxml() {
        return path_to_fxml;
    }

    public boolean isResizable() {
        return resizable;
    }

    public boolean isWait_termination(){
        return wait_termination;
    }
}