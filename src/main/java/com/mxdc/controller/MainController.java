package com.mxdc.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {
    @FXML
    private BorderPane root;  //主窗体BorderPane
    @FXML
    private Label labelMinimize;  //标题栏的最小化Label按钮
    @FXML
    private Label labelMaximize;  //标题栏的最大化Label按钮
    @FXML
    private Label labelExit;  //标题栏的关闭/退出Label按钮
    @FXML
    private BorderPane titleBar;  //包裹标题文字和最小化、最大化、关闭/退出按钮的BorderPane
    @FXML
    private VBox vBox;      //包裹“上传区、相册、图床设置”三个标签的VBox
    @FXML
    private HBox hBoxUpload;  //上传区HBox标签
    @FXML
    private HBox hBoxPhotograph;  //相册HBox标签
    @FXML
    private HBox hBoxPictureBedSettings;  //图床设置HBox标签
    private boolean flag = false;  //用作“图床设置”是否展开的状态标记

    private Node node;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labelMinimize.setCursor(Cursor.DEFAULT);
        labelMaximize.setCursor(Cursor.DEFAULT);
        labelExit.setCursor(Cursor.DEFAULT);
        titleBar.setCursor(Cursor.DEFAULT);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/settingsItem.fxml"));

            node = loader.load();
        }
        catch (Exception e){
        e.printStackTrace();
    }

    }

    //最小化Label按钮事件处理
    @FXML
    public void onClickedMinimize(MouseEvent mouseEvent) {  //最小化按钮鼠标单击事件
        if (mouseEvent.getButton() == MouseButton.PRIMARY) { //如果按下鼠标左键，最小化primaryStage
            Stage primaryStage = (Stage) labelMinimize.getParent().getScene().getWindow();  //窗体primaryStage对象
            primaryStage.setIconified(true);
        }
    }

    @FXML
    public void onEnteredMinimize(MouseEvent mouseEvent) {  //最小化按钮鼠标进入事件
        if (labelMinimize.getCursor() == Cursor.DEFAULT) {
            labelMinimize.setGraphic(new ImageView(new Image("/image/Minimize.png", 46, 32, false, false, false)));
        }
    }

    @FXML
    public void onExitedMinimize(MouseEvent mouseEvent) {  //最小化按钮鼠标推退出事件
        if (labelMinimize.getCursor() == Cursor.DEFAULT) {
            labelMinimize.setGraphic(new ImageView(new Image("/image/MinimizeDefault.png", 46, 32, false, false, false)));
        }
    }

    //最大化Label按钮事件处理
    @FXML
    public void onClickedMaximize(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {  //如果按下鼠标左键，最大化/最小化primaryStage
            Stage primaryStage = (Stage) labelMaximize.getParent().getScene().getWindow();  //窗体primaryStage对象
            if (!primaryStage.isMaximized()) {  //如果primaryStage是最小化，设置成最大化
                primaryStage.setMaximized(true);
                labelMaximize.setGraphic(new ImageView(new Image("/image/MaximizedDefault.png", 46, 32, false, false, false)));
                //设置primaryStage高度、宽度为屏幕的可视化高度、宽度（不包括Windows底下的任务栏）
                primaryStage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
                primaryStage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
            } else {  //如果primaryStage不是最小化，设置成最小化
                primaryStage.setMaximized(false);
                labelMaximize.setGraphic(new ImageView(new Image("/image/MaximizeDefault.png", 46, 32, false, false, false)));
            }
        }
    }

    @FXML
    public void onEnteredMaximize(MouseEvent mouseEvent) {  //最大化按钮鼠标进入事件
        if (labelMaximize.getCursor() == Cursor.DEFAULT) {
            Stage primaryStage = (Stage) labelMaximize.getParent().getScene().getWindow();  //窗体primaryStage对象
            if (!primaryStage.isMaximized()) {
                labelMaximize.setGraphic(new ImageView(new Image("/image/Maximize.png", 46, 32, false, false, false)));
            } else {
                labelMaximize.setGraphic(new ImageView(new Image("/image/Maximized.png", 46, 32, false, false, false)));
            }
        }
    }

    @FXML
    public void onExitedMaximize(MouseEvent mouseEvent) {  //最大化按钮鼠标推退出事件
        if (labelMaximize.getCursor() == Cursor.DEFAULT) {
            Stage primaryStage = (Stage) labelMaximize.getParent().getScene().getWindow();  //窗体primaryStage对象
            if (!primaryStage.isMaximized()) {
                labelMaximize.setGraphic(new ImageView(new Image("/image/MaximizeDefault.png", 46, 32, false, false, false)));
            } else {
                labelMaximize.setGraphic(new ImageView(new Image("/image/MaximizedDefault.png", 46, 32, false, false, false)));
            }
        }
    }

    //关闭/退出按钮事件处理
    @FXML
    public void onClickedExit(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {  //如果按下鼠标左键，关闭primaryStage
            Stage primaryStage = (Stage) labelExit.getParent().getScene().getWindow();  //窗体primaryStage对象
            primaryStage.close();
        }
    }

    @FXML
    public void onEnteredExit(MouseEvent mouseEvent) {  //关闭/退出按钮鼠标进入事件
        if (labelExit.getCursor() == Cursor.DEFAULT) {
            labelExit.setGraphic(new ImageView(new Image("/image/Exit.png", 46, 32, false, false, false)));
        }
    }

    @FXML
    public void onExitedExit(MouseEvent mouseEvent) {  //关闭/退出按钮鼠标推退出事件
        if (labelExit.getCursor() == Cursor.DEFAULT) {
            labelExit.setGraphic(new ImageView(new Image("/image/ExitDefault.png", 46, 32, false, false, false)));
        }
    }

    //包裹标题文字和最小化、最大化、关闭/退出按钮的BorderPane拖拽事件
    private double titleBarMousePressedX;  //记录titleBar鼠标按下时的X坐标（即SceneX或X）
    private double titleBarMousePressedY;  //记录titleBar鼠标按下时的Y坐标（即SceneY或Y）

    @FXML
    public void onTitleBarPressed(MouseEvent mouseEvent) {  //BorderPane鼠标按下事件
        if (titleBar.getCursor() == Cursor.DEFAULT) {
            //如果按下的位置不是最小化、最大化、关闭/退出按钮的范围，记录按下的X、Y坐标
            if (mouseEvent.getSceneX() < titleBar.getWidth() - (labelMinimize.getWidth() + labelMaximize.getWidth() + labelExit.getWidth())) {
                titleBarMousePressedX = mouseEvent.getX();
                titleBarMousePressedY = mouseEvent.getY();
            }
        }
    }

    @FXML
    public void onTitleBarDragged(MouseEvent mouseEvent) {  //BorderPane鼠标拖拽事件
        if (titleBar.getCursor() == Cursor.DEFAULT) {
            if (!labelMinimize.isPressed() && !labelMaximize.isPressed() && !labelExit.isPressed()) {
                Stage primaryStage = (Stage) titleBar.getParent().getScene().getWindow();
                //如果鼠标的屏幕位置ScreenX、Y在屏幕的可视化区域内，才执行移动窗体操作
                if (0 <= mouseEvent.getScreenX() && mouseEvent.getScreenX() <= Screen.getPrimary().getVisualBounds().getWidth()
                        && 0 <= mouseEvent.getScreenY() && mouseEvent.getScreenY() <= Screen.getPrimary().getVisualBounds().getHeight()) {
                    if (primaryStage.isMaximized()) {  //如果是最大化状态下拖拽，变为未最大化的状态
                        //记录计算按下鼠标时的百分比(Y坐标不需要计算，因为Y坐标本身没有变化)
                        double validTitleBarWidth = primaryStage.getWidth() - labelMinimize.getWidth() - labelMaximize.getWidth() - labelExit.getWidth();
                        double percentageX = titleBarMousePressedX / validTitleBarWidth;
                        //设置成未最大化的状态
                        primaryStage.setMaximized(false);
                        labelMaximize.setGraphic(new ImageView(new Image("/image/MaximizeDefault.png", 46, 32, false, false, false)));
                        //重新计算未最大化的状态的鼠标按下坐标
                        validTitleBarWidth = primaryStage.getWidth() - labelMinimize.getWidth() - labelMaximize.getWidth() - labelExit.getWidth();
                        titleBarMousePressedX = validTitleBarWidth * percentageX;
                        //更新主舞台的坐标
                        primaryStage.setX(mouseEvent.getScreenX() - titleBarMousePressedX);
                        primaryStage.setY(mouseEvent.getScreenY() - titleBarMousePressedY);
                    } else {  //否则为最大化状态，直接更新主舞台的坐标
                        primaryStage.setX(mouseEvent.getScreenX() - this.titleBarMousePressedX);
                        primaryStage.setY(mouseEvent.getScreenY() - this.titleBarMousePressedY);
                    }
                }
            }
        }
    }

    @FXML
    public void onTitleBarClicked(MouseEvent mouseEvent) {
        if (titleBar.getCursor() == Cursor.DEFAULT) {
            //如果鼠标的位置不是最小化、最大化、关闭/退出按钮的范围
            if (mouseEvent.getSceneX() < titleBar.getWidth() - (labelMinimize.getWidth() + labelMaximize.getWidth() + labelExit.getWidth())) {
                if (mouseEvent.getClickCount() == 2) {
                    this.onClickedMaximize(mouseEvent);
                }
            }
        }
    }


    //上传区tag事件
    public void onUploadClicked(MouseEvent mouseEvent){
        if (mouseEvent.getButton() == MouseButton.PRIMARY){
            this.resetLeftTagStatus();   //调用重设标签的图片和文字颜色函数
            ((Label)hBoxUpload.getChildren().get(0)).setGraphic(new ImageView(new Image("/image/cloud_focused.png",30, 25, false, false, false)));
            ((Label)hBoxUpload.getChildren().get(1)).setTextFill(Color.rgb(64,158,255));

            root.setCenter(null);
        }
    }
    //相册tag事件
    public void onPhotographClicked(MouseEvent mouseEvent){
        if (mouseEvent.getButton() == MouseButton.PRIMARY){
            this.resetLeftTagStatus();   //调用重设标签的图片和文字颜色函数
            ((Label)hBoxPhotograph.getChildren().get(0)).setGraphic(new ImageView(new Image("/image/photograph_focused.png",30, 25, false, false, false)));
            ((Label)hBoxPhotograph.getChildren().get(1)).setTextFill(Color.rgb(64,158,255));

            root.setCenter(new Label("相册"));
        }
    }
    //图床设置tag事件
    public void onPictureBedClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            this.resetLeftTagStatus();  //调用重设标签的图片和文字颜色函数
            ((Label)hBoxPictureBedSettings.getChildren().get(0)).setGraphic(new ImageView(new Image("/image/picturebedsettings_focused.png",30, 25, false, false, false)));
            ((Label)hBoxPictureBedSettings.getChildren().get(1)).setTextFill(Color.rgb(64,158,255));
            if (mouseEvent.getClickCount() == 2){
                this.onFlagIconClicked(mouseEvent);
            }
        }
    }
    public void onFlagIconClicked(MouseEvent mouseEvent){
        if (mouseEvent.getButton() == MouseButton.PRIMARY){
            if (flag) {
                flag = false;
                ((Label)hBoxPictureBedSettings.getChildren().get(2)).setGraphic(new ImageView(new Image("/image/fold.png", 10, 10, false, false, false)));
                vBox.getChildren().remove(node);
            } else {
                flag = true;
                ((Label)hBoxPictureBedSettings.getChildren().get(2)).setGraphic(new ImageView(new Image("/image/unfold.png", 10, 10, false, false, false)));
                vBox.getChildren().addAll(node);
            }
        }
    }
    //左边上传区HBox标签、相册HBox标签、图床设置HBox标签的图片和文字重设为默认值的函数
    private void resetLeftTagStatus(){
        ((Label)hBoxUpload.getChildren().get(0)).setGraphic(new ImageView(new Image("/image/cloud.png",30, 25, false, false, false)));
        ((Label)hBoxUpload.getChildren().get(1)).setTextFill(Color.rgb(255,255,255));
        ((Label)hBoxPhotograph.getChildren().get(0)).setGraphic(new ImageView(new Image("/image/photograph.png",30, 25, false, false, false)));
        ((Label)hBoxPhotograph.getChildren().get(1)).setTextFill(Color.rgb(255,255,255));
        ((Label)hBoxPictureBedSettings.getChildren().get(0)).setGraphic(new ImageView(new Image("/image/picturebedsettings.png",30, 25, false, false, false)));
        ((Label)hBoxPictureBedSettings.getChildren().get(1)).setTextFill(Color.rgb(255,255,255));
    }
}