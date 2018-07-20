package com.talfinder.che.ide.ext.help.client;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.eclipse.che.ide.api.action.ActionManager;
import org.eclipse.che.ide.api.action.DefaultActionGroup;
import org.eclipse.che.ide.api.action.IdeActions;
import org.eclipse.che.ide.api.extension.Extension;
import org.eclipse.che.ide.api.keybinding.KeyBindingAgent;
import org.eclipse.che.ide.api.keybinding.KeyBuilder;
import org.eclipse.che.ide.util.input.KeyCodeMap;

@Singleton
@Extension(title = "TalFinderHelpExtension", version = "3.0.0")
public class TalFinderHelpExtension {

  public static final String SHOW_TALFINDER_DOCS = "showTalFinderDocs";
  public static final String HIDE_TALFINDER_DOCS = "hideTalFinderDocs";

  @Inject
  public TalFinderHelpExtension(
      ActionManager actionManager,
      KeyBindingAgent keyBinding,
      final ShowDocsAction showDocsAction,
      final HideDocsAction hideDocsAction) {
    DefaultActionGroup helpGroup =
        (DefaultActionGroup) actionManager.getAction(IdeActions.GROUP_HELP);
    actionManager.registerAction(SHOW_TALFINDER_DOCS, showDocsAction);
    actionManager.registerAction(HIDE_TALFINDER_DOCS, hideDocsAction);
    helpGroup.add(showDocsAction);

    keyBinding
        .getGlobal()
        .addKey(new KeyBuilder().charCode(KeyCodeMap.F1).build(), SHOW_TALFINDER_DOCS);

    keyBinding
        .getGlobal()
        .addKey(new KeyBuilder().charCode(KeyCodeMap.ESC).build(), HIDE_TALFINDER_DOCS);
  }
}
