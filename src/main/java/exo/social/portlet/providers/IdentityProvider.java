/*
 * Copyright (C) 2003-2011 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package exo.social.portlet.providers;

import exo.social.portlet.Controller;
import exo.social.portlet.qualifiers.Current;
import org.exoplatform.social.core.identity.provider.OrganizationIdentityProvider;
import org.exoplatform.social.core.identity.model.Identity;
import org.exoplatform.social.core.manager.IdentityManager;
import org.exoplatform.web.application.RequestContext;

import javax.inject.Inject;
import javax.inject.Provider;

/** @author <a href="mailto:alain.defrance@exoplatform.com">Alain Defrance</a> */
public class IdentityProvider implements Provider<Identity> {

  @Inject IdentityManager im;

  @Current
  public Identity get() {

    String remote = RequestContext.getCurrentInstance().getRemoteUser();
    Identity i = im.getOrCreateIdentity(OrganizationIdentityProvider.NAME, remote, true);

    Controller.applyDefaultAvatar(i);

    return i;
  }

}
