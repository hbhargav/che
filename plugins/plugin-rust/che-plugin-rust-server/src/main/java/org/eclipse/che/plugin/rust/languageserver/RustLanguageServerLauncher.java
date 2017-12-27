/*
 * Copyright (c) 2012-2017 Red Hat, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package org.eclipse.che.plugin.rust.languageserver;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import org.eclipse.che.api.languageserver.exception.LanguageServerException;
import org.eclipse.che.api.languageserver.launcher.LanguageServerLauncher;
import org.eclipse.che.api.languageserver.launcher.LanguageServerLauncherTemplate;
import org.eclipse.che.api.languageserver.registry.DocumentFilter;
import org.eclipse.che.api.languageserver.registry.LanguageServerDescription;
import org.eclipse.che.api.languageserver.registry.ServerInitializerObserver;
import org.eclipse.che.plugin.rust.inject.RustModule;
import org.eclipse.lsp4j.ServerCapabilities;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageServer;

/** @author Alexander Andrienko */
/** @author Hanno Kolvenbach */
@Singleton
public class RustLanguageServerLauncher extends LanguageServerLauncherTemplate
    implements ServerInitializerObserver {

  // private static final String LANGUAGE_ID = "rust";
  // private static final LanguageDescription description;
  // private static final String[] EXTENSIONS = new String[] {"c", "cpp", "h", "cc"}; // todo
  // private static final String[] MIME_TYPES =
  //     new String[] {"text/x-c", "text/x-c", "text/x-h", "text/x-c"}; // todo check it
  private static final LanguageServerDescription DESCRIPTION = createServerDescription();
  private static final String REGEX = ".*\\.(rs|RS)";
  private final Path launchScript;

  @Inject
  public RustLanguageServerLauncher() {
    launchScript = Paths.get(System.getenv("HOME"), "che/ls-rust/launch.sh");
  }

  @Override
  public boolean isAbleToLaunch() {
    return Files.exists(launchScript);
  }

  // @Override
  // public LanguageDescription getLanguageDescription() {
  //   return description;
  // }

  @Override
  protected LanguageServer connectToLanguageServer(
      Process languageServerProcess, LanguageClient client) throws LanguageServerException {
    Launcher<LanguageServer> launcher =
        Launcher.createLauncher(
            client,
            LanguageServer.class,
            languageServerProcess.getInputStream(),
            languageServerProcess.getOutputStream());
    launcher.startListening();
    return launcher.getRemoteProxy();
  }

  protected Process startLanguageServerProcess(String projectPath) throws LanguageServerException {
    ProcessBuilder processBuilder = new ProcessBuilder(launchScript.toString());
    processBuilder.redirectInput(ProcessBuilder.Redirect.PIPE);
    processBuilder.redirectOutput(ProcessBuilder.Redirect.PIPE);
    processBuilder.redirectErrorStream(false);
    try {
      return processBuilder.start();
    } catch (IOException e) {
      throw new LanguageServerException("Can't start rls language server", e);
    }
  }

  @Override
  public void onServerInitialized(
      LanguageServerLauncher launcher,
      LanguageServer server,
      ServerCapabilities capabilities,
      String projectPath) {
    // LOG.debug("rust language server initialized");
  }

  @Override
  public LanguageServerDescription getDescription() {
    return DESCRIPTION;
  }

  private static LanguageServerDescription createServerDescription() {
    LanguageServerDescription description =
        new LanguageServerDescription(
            "org.eclipse.che.plugin.rust.languageserver",
            null,
            Arrays.asList(new DocumentFilter(RustModule.LANGUAGE_ID, REGEX, null)));
    return description;
  }

  // static {
  //   description = new LanguageDescription();
  //   description.setFileExtensions(asList(EXTENSIONS));
  //   description.setLanguageId(LANGUAGE_ID);
  //   description.setMimeTypes(Arrays.asList(MIME_TYPES));
  // }
}
