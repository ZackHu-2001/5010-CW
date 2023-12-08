package view;

import model.ReadOnlyModel;

/**
 * View factory to create instances of Views.
 */
public class ViewFactory {

  /**
   * Create a view based on the given view type.
   *
   * @param viewType  the type of view to be created
   * @param model     the model to be read from
   *                  (only used when viewType is "GUI")
   * @return          the view created
   */
  public View createView(String viewType, ReadOnlyModel model) {
    if ("CMD".equalsIgnoreCase(viewType)) {
      return new NullView();
    } else {
      return new GuiView(model);
    }
  }
}
