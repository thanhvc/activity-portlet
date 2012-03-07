package exo.social.portlet;

import exo.social.portlet.qualifiers.Current;
import org.exoplatform.social.core.activity.model.ExoSocialActivity;
import org.exoplatform.social.core.activity.model.ExoSocialActivityImpl;
import org.exoplatform.social.core.identity.model.Identity;
import org.exoplatform.social.core.manager.ActivityManager;
import org.exoplatform.social.core.manager.IdentityManager;
import org.exoplatform.social.core.service.LinkProvider;
import org.juzu.Action;
import org.juzu.Path;
import org.juzu.Resource;
import org.juzu.Response;
import org.juzu.SessionScoped;
import org.juzu.View;
import org.juzu.plugin.ajax.Ajax;
import org.juzu.template.Template;
import exo.social.portlet.models.Activity;
import exo.social.portlet.templates.comments;
import exo.social.portlet.templates.index;
import exo.social.portlet.templates.likes;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** @author <a href="mailto:alain.defrance@exoplatform.com">Alain Defrance</a> */
public class Controller
{

  @Inject ActivityManager am;
  @Inject IdentityManager im;

  @Inject @Path("form.gtmpl")     Template form;
  @Inject @Path("index.gtmpl")    index index;
  @Inject @Path("likes.gtmpl")    likes likes;
  @Inject @Path("comments.gtmpl") comments comments;

  @Inject @Current @SessionScoped Identity currentUser;

  @View
  public void index() throws IOException
  {
    form.render();
  }

  @View
  public void activity(String activityId) throws IOException
  {
    
    index
        .with()
        .activityId(activityId)
        .render();

  }
  
  @Ajax
  @Resource
  public void doLike(String id) {

    ExoSocialActivity activity = am.getActivity(id);
    List<Identity> likers = likers(activity);
    boolean isLiking = likers.contains(currentUser);

    // remove like
    if (isLiking) {
      List<String> likes = new ArrayList<String>(Arrays.asList(activity.getLikeIdentityIds()));
      likes.remove(currentUser.getId());
      likers.remove(currentUser);
      activity.setLikeIdentityIds(likes.toArray(new String[]{}));
      am.updateActivity(activity);
    }
    // add like
    else {
      List<String> likes = new ArrayList<String>(Arrays.asList(activity.getLikeIdentityIds()));
      likes.add(currentUser.getId());
      likers.add(currentUser);
      activity.setLikeIdentityIds(likes.toArray(new String[]{}));
      am.updateActivity(activity);
    }

    likes
        .with()
        .likers(likers)
        .isLiking(!isLiking)
        .render();
  }

  @Ajax
  @Resource
  public void loadLike(String id) {
    
    ExoSocialActivity activity = am.getActivity(id);
    List<Identity> likers = likers(activity);
    boolean isLiking = likers.contains(currentUser);

    likes
        .with()
        .likers(likers)
        .isLiking(isLiking)
        .render();
  }

  @Ajax
  @Resource
  public void doComment(String content, String activityId) {

    //
    ExoSocialActivity activity = am.getActivity(activityId);
    ExoSocialActivity comment = new ExoSocialActivityImpl(currentUser.getId(), null, content);

    //
    am.saveComment(activity, comment);

    //
    List<Activity> comments = comments(activity);

    this.comments
        .with()
        .comments(comments)
        .render();
    
  }

  @Ajax
  @Resource
  public void loadComment(String id) {

    ExoSocialActivity activity = am.getActivity(id);
    List<Activity> comments = comments(activity);

    this.comments
        .with()
        .comments(comments)
        .render();
  }

  @Ajax
  @Resource
  public void deleteComment(String activityId, String commentId) {

    am.deleteComment(activityId, commentId);
    
  }

  @Action
  public Response loadActivity(String activityId) {
    return Controller_.activity(activityId);
  }

  @Action
  public Response back() {
    return Controller_.index();
  }

  private List<Identity> likers(ExoSocialActivity activity) {

    List<Identity> likers = new ArrayList<Identity>();
    for (String likerId : activity.getLikeIdentityIds()) {
      Identity liker = im.getIdentity(likerId, true);
      applyDefaultAvatar(liker);
      likers.add(liker);
    }

    return likers;

  }

  private List<Activity> comments(ExoSocialActivity activity) {

    List<Activity> comments = new ArrayList<Activity>();
    for (ExoSocialActivity data : am.getCommentsWithListAccess(activity).loadAsList(0, 10)) {
      Identity poster = im.getIdentity(data.getUserId(), true);
      applyDefaultAvatar(poster);
      comments.add(new Activity(poster, data, poster.getId().equals(currentUser.getId())));
    }

    return comments;

  }

  // TODO : remove this crap when social will be able to do it smartly
  public static void applyDefaultAvatar(Identity i) {
    
    if (i.getProfile().getAvatarUrl() == null) {
      i.getProfile().setAvatarUrl(LinkProvider.PROFILE_DEFAULT_AVATAR_URL);
    }

  }


}
