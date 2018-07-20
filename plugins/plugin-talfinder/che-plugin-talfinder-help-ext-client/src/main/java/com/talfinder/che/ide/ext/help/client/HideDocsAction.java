package com.talfinder.che.ide.ext.help.client;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.eclipse.che.ide.api.action.ActionEvent;
import org.eclipse.che.ide.api.action.BaseAction;

@Singleton
public class HideDocsAction extends BaseAction {

  @Inject
  public HideDocsAction(TalFinderHelpExtensionLocConst locale) {
    super(locale.actionHideDocsTitle(), locale.actionHideDocsDescription());
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    hideDocs();
  }

  private static native void hideDocs() /*-{
        console.log("In GWT-->hideTalFinderDocument ");
        $wnd.parent.postMessage("TF_HIDE_DOCS_KEYBOARD", "*");
    }-*/;
}
