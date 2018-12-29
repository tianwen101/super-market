package supermarket;

import com.soft1841.book.dao.BookDAO;
        import com.soft1841.VIP.entity.Book;
        import com.soft1841.VIP.entity.Type;
        import com.soft1841.VIP.service.BookService;
        import com.soft1841.VIP.service.TypeService;
        import com.soft1841.VIP.utils.ServiceFactory;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.control.Alert;
        import javafx.scene.control.ComboBox;
        import javafx.scene.control.TextArea;
        import javafx.scene.control.TextField;
        import javafx.stage.Stage;

        import java.net.URL;
        import java.util.List;
        import java.util.ResourceBundle;

public class AddVIPController implements Initializable {
    private ObservableList<VIP> bookData = FXCollections.observableArrayList();

    public ObservableList<VIP> getVIPData() {
        return VIPData;
    }

    public void setVIPData(ObservableList<VIP> VIPData) {
        this.VIPData = VIPData;
    }

    @FXML
    private ComboBox<Type> bookType;

    @FXML
    private TextField VIPName, VIPAuthor, VIPPrice, VIPCover, VIPStock;
    @FXML
    private TextArea VIPSummary;

    private ObservableList<Type> typeData = FXCollections.observableArrayList();

    private VIPService VIPService = ServiceFactory.getVIPServiceInstance();

    private TypeService typeService = ServiceFactory.getTypeServiceInstance();

    private List<Type> typeList = null;

    private Long typeId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeList = typeService.getAllTypes();
        typeData.addAll(typeList);
        VIPType.setItems(typeData);
        VIPType.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
                    typeId = newValue.getId();
                }
        );
    }

    public void addVIP() {
        String name = VIPName.getText();
        String author = VIPAuthor.getText();
        String price = VIPPrice.getText();
        String stock = VIPStock.getText();
        String cover = VIPCover.getText();
        String summary = VIPSummary.getText();
        System.out.println(stock);
        VIP VIP = new VIP();
        VIP.setTypeId(typeId);
        VIP.setName(name);
        VIP.setAuthor(author);
        VIP.setPrice(Double.parseDouble(price));
        VIP.setStock(Integer.parseInt(stock));
        VIP.setCover(cover);
        VIP.setSummary(summary);
        long id = VIPService.addVIP(VIP);
        VIP.setId(id);
        this.getVIPData().add(VIP);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示信息");
        alert.setHeaderText("新增hy成功!");
        alert.showAndWait();
        Stage stage = (Stage) VIPName.getScene().getWindow();
        stage.close();
    }
}