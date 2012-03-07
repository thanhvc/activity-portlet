$(function() {

  // Like/Unlike
  $('.jz').on("click", ".like", function() {
    var likeUrl = this.ActivityApplication().doLike();
    var container = this.$find(".like-container");
    var activityId = this.$find(".activity-data").data('activityid');
    container.load(likeUrl, {id: activityId});
  });

  // Add comment
  $('.jz').on("click", ".comment", function() {
    var commentUrl = this.ActivityApplication().doComment();
    var container = this.$find(".comment-container");
    var activityId = this.$find(".activity-data").data('activityid');
    var content = this.$find(".comment-content").val();
    container.load(commentUrl, {activityId: activityId, content: content});
  });

  // Delete comment
  $('.jz').on("click", ".delete-comment", function(e) {
    var commentUrl = this.ActivityApplication().deleteComment();
    var activityId = this.$find(".activity-data").data('activityid');
    var commentId = $(e.target).data('commentid');
    $(e.target).parent().parent().remove();

    $.ajax({
        url: commentUrl,
        data: {activityId: activityId, commentId: commentId}
    });
      
  });

  // Load initial likes
  $(".like-container").each(function() {
    var likeUrl = this.ActivityApplication().loadLike();
    var container = this.$find(".like-container");
    var activityId = this.$find(".activity-data").data('activityid');
    container.load(likeUrl, {id: activityId});

  });

  // Load initial comments
  $(".comment-container").each(function() {
    var commentUrl = this.ActivityApplication().loadComment();
    var container = this.$find(".comment-container");
    var activityId = this.$find(".activity-data").data('activityid');
    container.load(commentUrl, {id: activityId});
  });

});