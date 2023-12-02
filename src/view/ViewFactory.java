package view;

import model.ReadOnlyModel;

/**
 * View factory to create instances of Views.
 */
public class ViewFactory {

  public View createView(String viewType, ReadOnlyModel model) {
    if ("CMD".equalsIgnoreCase(viewType)) {
      return new NullView();
    } else {
        return new GUIView(model);
      }
    }
}
