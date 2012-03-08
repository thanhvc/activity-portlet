@Application(
    name = "ActivityApplication",
    plugins = {
        AssetPlugin.class,
        AjaxPlugin.class
    })
@Bindings({
  @Binding(value = ActivityManager.class, implementation = GateInMetaProvider.class),
  @Binding(value = IdentityManager.class, implementation = GateInMetaProvider.class),
  @Binding(value = Identity.class, implementation = IdentityProvider.class)
})
@Assets(
  scripts = {
    @Script(src = "js/jquery-1.7.1.js"),
    @Script(src = "js/less-1.2.2.min.js"),
    @Script(src = "js/bootstrap.js"),
    @Script(src = "js/bootstrap-collapse.js"),
    @Script(src = "js/bootstrap-tooltip.js"),
    @Script(src = "js/bootstrap-popover.js"),
    @Script(src = "js/activity.js")
  },
  stylesheets = {
    @Stylesheet(src = "css/gatein.less")
  }
)
package exo.social.portlet;

import exo.social.portlet.providers.GateInMetaProvider;
import exo.social.portlet.providers.IdentityProvider;
import org.exoplatform.social.core.identity.model.Identity;
import org.exoplatform.social.core.manager.ActivityManager;
import org.exoplatform.social.core.manager.IdentityManager;
import org.juzu.Application;
import org.juzu.inject.Binding;
import org.juzu.inject.Bindings;
import org.juzu.plugin.ajax.AjaxPlugin;
import org.juzu.plugin.asset.AssetPlugin;
import org.juzu.plugin.asset.Assets;
import org.juzu.plugin.asset.Script;
import org.juzu.plugin.asset.Stylesheet;