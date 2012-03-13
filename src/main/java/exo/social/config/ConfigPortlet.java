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

package exo.social.config;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author <a href="mailto:alain.defrance@exoplatform.com">Alain Defrance</a>
 */
public class ConfigPortlet extends GenericPortlet {

  @Override
  public void processAction(final ActionRequest request, final ActionResponse response) throws PortletException, IOException {

    //
    String namespace = request.getParameter("namespace");
    String integrationPoint = request.getParameter("integrationPoint");

    //
    if ("".equals(namespace)) {
      namespace = null;
    }
    if ("".equals(integrationPoint)) {
      integrationPoint = null;
    }

    //
    if (namespace != null) {
      response.setRenderParameter("socNamespace", namespace);
    }
    else {
      response.removePublicRenderParameter("socNamespace");
    }

    //
    if (integrationPoint != null) {
      response.setRenderParameter("socIntegrationPoint", integrationPoint);
    }
    else {
      response.removePublicRenderParameter("socIntegrationPoint");
    }

  }

  @Override
  protected void doView(final RenderRequest request, final RenderResponse response) throws PortletException, IOException {

    PrintWriter writer = response.getWriter();

    String namespace = request.getParameter("socNamespace");
    String integrationPoint = request.getParameter("socIntegrationPoint");

    if (namespace == null) {
      namespace = "";
    }

    if (integrationPoint == null) {
      integrationPoint = "";
    }

    writer.append("<form method='POST' action='" + response.createActionURL().toString() + "'>");
    writer.append("socNamespace : <input name='namespace' value='" + namespace + "'/><br/>");
    writer.append("socIntegrationPoint : <input name='integrationPoint' value='" + integrationPoint + "'/><br/>");
    writer.append("<input type='submit'/>");
    writer.append("</form>");

  }
}
