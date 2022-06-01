module isep.godefroy.ricochet.ricochet_robot {
    requires javafx.controls;
    requires javafx.fxml;


    opens isep.godefroy.ricochet.ricochet_robot to javafx.fxml;
    exports isep.godefroy.ricochet.ricochet_robot;
}