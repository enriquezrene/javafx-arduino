package ec.edu.udla.ui.regions;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;

public abstract class AbstractController implements DrawableRegion {

    protected RegionsContainer container;

    protected void addColumnToTable(TableColumn column, TableView table) {
        table.getColumns().add(column);
    }

    public void atras(){
        if(container.getPantallasVisitadas().size()>1){
            container.setCurrentScreen(container.getPantallasVisitadas().get(1));
        }
    }

    protected TableColumn createColumn(String columnHeader, String fieldName, int ancho) {
        TableColumn column = new TableColumn<>(columnHeader);
        column.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        column.setMaxWidth(1f * Integer.MAX_VALUE * ancho);
        return column;
    }

    @Override
    public void setContainer(RegionsContainer container) {
        this.container = container;
    }

    protected void limpiarTextInputs(TextInputControl... textFields) {
        for (int i = 0; i < textFields.length; i++) {
            textFields[i].setText("");
        }
    }
}
