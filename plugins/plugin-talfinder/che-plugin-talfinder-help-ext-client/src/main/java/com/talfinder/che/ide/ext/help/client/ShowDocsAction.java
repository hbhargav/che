package com.talfinder.che.ide.ext.help.client;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.eclipse.che.ide.api.action.ActionEvent;
import org.eclipse.che.ide.api.action.BaseAction;

@Singleton
public class ShowDocsAction extends BaseAction {

  @Inject
  public ShowDocsAction(
      TalFinderHelpExtensionLocConst locale, TalFinderHelpExtensionResources resources) {
    super(locale.actionShowDocsTitle(), locale.actionShowDocsDescription(), resources.showDocs());
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    showDocs();
  }

  private static native void showDocs() /*-{
        console.log("In GWT-->showTalFinderDocument ");
        $wnd.parent.postMessage("TF_SHOW_DOCS", "*");
    }-*/;
}
