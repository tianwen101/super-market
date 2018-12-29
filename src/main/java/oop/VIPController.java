package supermarket;

import cn.hutool.db.Entity;
import com.soft1841.VIP.dao.BookDAO;
import com.soft1841.VIP.dao.TypeDAO;
import com.soft1841.VIP.entity.Book;
import com.soft1841.VIP.entity.Type;
import com.soft1841.VIP.service.BookService;
import com.soft1841.VIP.service.TypeService;
import com.soft1841.VIP.utils.ComponentUtil;
import com.soft1841.VIP.utils.DAOFactory;
import com.soft1841.VIP.utils.ExcelExport;
import com.soft1841.VIP.utils.ServiceFactory;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oop.VIP;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class VIPController implements Initializable {
    //布局文件中的表格视图对象，用来显示数据库中读取的所有图书信息
    @FXML
    private TableView<VIP> VIPTable;

    //布局文件中的下拉框组件对象，用来显示数据库中读取的所有图书类别
    @FXML
    private ComboBox<Type> typeComboBox;

    //布局文件中的输入文本框对象，用来输入搜索关键词
    @FXML
    private TextField keywordsField;

    //图书模型数据集合，可以实时相应数据变化，无需刷新
    private ObservableList<VIP> VIPData = FXCollections.observableArrayList();

    //图书类型模型数据集合
    private ObservableList<Type> typeData = FXCollections.observableArrayList();

    //图书Service对象，从DAO工厂通过静态方法获得
    private VIPService VIPService = ServiceFactory.getBookServiceInstance();

    //类别TypeService对象
    private TypeService typeService = ServiceFactory.getTypeServiceInstance();

    //图书集合，存放数据库图书表各种查询的结果
    private List<VIP> VIPList = null;

    //类别集合，存放数据库类别表查询结果
    private List<Type> typeList = null;

    //表格中的编辑列
    private TableColumn<VIP, VIP> editCol = new TableColumn<>("操作");

    //表格中的删除列
    private TableColumn<VIP, VIP> delCol = new TableColumn<>("操作");

    //初始化方法，通过调用对图书表格和列表下拉框的两个封装方法，实现数据初始化
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        initComBox();
    }

    //表格初始化方法
    private void initTable() {
        //水平方向不显示滚动条，表格的列宽会均匀分布
        VIPTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //1.调用查询所有图书的方法，
        VIPList = VIPService.getAllVIP();
        //将实体集合作为参数，调用显示数据的方法，可以在界面的表格中显示图书模型集合的值
        showVIPData(VIPList);

        //2.编辑列的相关设置
        editCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        editCol.setCellFactory(param -> new TableCell<VIP, VIP>() {
            //通过ComponentUtil工具类的静态方法，传入按钮文字和样式，获得一个按钮对象
            private final Button editButton = ComponentUtil.getButton("编辑", "blue-theme");

            @Override
            protected void updateItem(VIP VIP, boolean empty) {
                super.updateItem(VIP, empty);
                if (VIP == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(editButton);
                //点击编辑按钮，弹出窗口，输入需要修改的图书价格
                editButton.setOnAction(event -> {
                    TextInputDialog dialog = new TextInputDialog("请输入号码");
                    dialog.setTitle("会员修改界面");
                    dialog.setHeaderText("会员：" + VIP.getName());
                    dialog.setContentText("请输入新的号码:");
                    Optional<String> result = dialog.showAndWait();
                    //确认输入了内容，避免NPE
                    if (result.isPresent()) {
                        //获取输入的新价格并转化成Double数据
                        String priceString = result.get();
                        VIP.setPrice(Double.parseDouble(priceString));
                        //更新图书信息
                        VIPService.updateVIP(VIP);
                    }
                });
            }
        });
        //将编辑列加入图书表格
        VIPTable.getColumns().add(editCol);

        //3.删除列的相关设置
        delCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        delCol.setCellFactory(param -> new TableCell<VIP, VIP>() {
            private final Button deleteButton = ComponentUtil.getButton("删除", "warning-theme");

            @Override
            protected void updateItem(VIP VIP, boolean empty) {
                super.updateItem(VIP, empty);
                if (VIP == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(deleteButton);
                //点击删除按钮，需要将这一行从表格移除，同时从底层数据库真正删除
                deleteButton.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("确认对话框");
                    alert.setHeaderText("会员：" + VIP.getName());
                    alert.setContentText("确定要删除这条会员吗?");
                    Optional<ButtonType> result = alert.showAndWait();
                    //点击了确认按钮，执行删除操作，同时移除一行模型数据
                    if (result.get() == ButtonType.OK) {
                        VIPData.remove(VIP);
                        VIPService.deleteVIP(VIP.getId());
                    }
                });
            }
        });
        //将除列加入图书表格
        VIPTable.getColumns().add(delCol);

        //4.图书表格双击事件,双击弹出显示图书详情的界面
        VIPTable.setRowFactory(tv ->

        {
            TableRow<VIP> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                //判断鼠标双击了一行
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    //获得该行的图书ID属性
                    long id = row.getItem().getId();
                    //根据id查询到图书的完整信息
                    VIP VIP = VIPService.getVIP(id);
                    //创建一个新的图书详情界面窗口
                    Stage VIPInfoStage = new Stage();
                    VIPInfoStage.setTitle("会员详情界面");
                    //用VBox显示具体图书信息
                    VBox vBox = new VBox();
                    vBox.setSpacing(10);
                    vBox.setAlignment(Pos.CENTER);
                    vBox.setPrefSize(600, 400);
                    vBox.setPadding(new Insets(10, 10, 10, 10));
                    Label nameLabel = new Label("会员：" + VIP.getName());
                    nameLabel.getStyleClass().add("font-title");
                    Label authorLabel = new Label("姓名：" + VIP.getAuthor());
                    Label priceLabel = new Label("价格:" + VIP.getPrice());
                    Label stockLabel = new Label("余额：" + VIP.getStock());
                    ImageView VIPImgView = new ImageView(new Image(VIP.getCover()));
                    VIPImgView.setFitHeight(150);
                    VIPImgView.setFitWidth(120);
                    Label summaryLabel = new Label(VIP.getSummary());
                    summaryLabel.setPrefWidth(400);
                    summaryLabel.setWrapText(true);
                    summaryLabel.getStyleClass().add("box");
                    vBox.getChildren().addAll(nameLabel, authorLabel, priceLabel, stockLabel, VIPImgView, summaryLabel);
                    Scene scene = new Scene(vBox, 640, 480);
                    //因为是一个新的窗口，需要重新读入一下样式表，这个界面就可以使用style.css样式表中的样式了
                    scene.getStylesheets().add("/css/style.css");
                    VIPInfoStage.setScene(scene);
                    VIPInfoStage.show();
                }
            });
            return row;
        });
    }

    //下拉框初始化方法
    private void initComBox() {
        //1.到数据库查询所有的类别
        typeList = typeService.getAllTypes();
        //2.将typeList集合加入typeData模型数据集合
        typeData.addAll(typeList);
        //3.将数据模型设置给下拉框
        typeComboBox.setItems(typeData);

        //4.下拉框选择事件监听，根据选择不同的类别，图书表格中会过滤出该类别的图书
        typeComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
                    // System.out.println(newValue.getId() + "," + newValue.getTypeName());
                    //移除掉之前的数据
            VIPTable.getItems().removeAll(VIPData);
                    //根据选中的类别查询该类别所有图书
            VIPList = VIPService.getVIPByTypeId(newValue.getId());
                    //重新显示数据
                    showVIPData(VIPList);
                }
        );
    }

    //显示图书表格数据的方法
    private void showVIPData(List<VIP> VIPList) {
        VIPData.addAll(VIPList);
        VIPTable.setItems(VIPData);
    }

    //弹出新增图书界面方法
    public void newVIPStage() throws Exception {
        Stage addVIPStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/add_VIP.fxml"));
        AnchorPane root = fxmlLoader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/css/style.css");
        AddVIPController addVIPController = fxmlLoader.getController();
        addVIPController.setVIPData(VIPData);
        addVIPStage.setTitle("新增会员界面");
        //界面大小不可变
        addVIPStage.setResizable(false);
        addVIPStage.setScene(scene);
        addVIPStage.show();
    }

    //根据关键词搜索方法
    public void search() {
        VIPTable.getItems().removeAll(VIPData);
        //获得输入的查询关键字
        String keywords = keywordsField.getText().trim();
        VIPList = VIPService.getVIPLike(keywords);
        showVIPData(VIPList);
    }

    //数据导出方法，采用hutool提供的工具类
    public void export() {
        ExcelExport.export(VIPList);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示信息");
        alert.setHeaderText("会员数据已删除!");
        alert.showAndWait();
    }
}
