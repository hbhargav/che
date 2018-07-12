/*
 * Copyright (c) 2012-2018 Red Hat, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package org.eclipse.che.api;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;
import org.eclipse.che.core.db.postgresql.PostgreSQLJndiDataSourceFactory;

/**
 * Creates appropriate JNDI data source factory instance depending on system variable.
 *
 * @author Max Shaposhnik (mshaposh@redhat.com)
 */
public class CommonJndiDataSourceFactory implements ObjectFactory {

  private final ObjectFactory delegate;

  public CommonJndiDataSourceFactory() throws Exception {
    delegate =
        Boolean.valueOf(System.getenv("CHE_MULTIUSER"))
            ? new PostgreSQLJndiDataSourceFactory()
            : new PostgreSQLJndiDataSourceFactory();
  }

  @Override
  public Object getObjectInstance(Object o, Name name, Context context, Hashtable<?, ?> hashtable)
      throws Exception {
    return delegate.getObjectInstance(o, name, context, hashtable);
  }
}
