from django.urls import path
from Drowsiness_Detection_App import views

urlpatterns = [
    path('', views.login, name="login"),
    path('register', views.register, name="register"),
    path('registered', views.registered, name="registered"),
    path('login_fun', views.login_fun, name="login_fun"),
    path('logout', views.logout, name="logout"),
    path('admin_home', views.admin_home, name="admin_home"),
    path('user_home', views.user_home, name="user_home"),


    # admin urls

    path('block_unblock', views.block_unblock, name="block_unblock"),
    path('block/<int:block_id>', views.block, name="block"),
    path('unblock/<int:unblock_id>', views.unblock, name="unblock"),
    path('add_manage_music', views.add_manage_music, name="add_manage_music"),
    path('add_music', views.add_music, name="add_music"),
    path('adding_musics', views.adding_musics, name="adding_musics"),
    path('delete/<int:delete_id>', views.delete, name="delete"),
    path('complaints_reply_admin', views.complaints_reply_admin, name="complaints_reply_admin"),
    path('reply_form/<int:reply_id>', views.reply_form, name="reply_form"),
    path('send_reply', views.send_reply, name="send_reply"),
    path('view_feedback', views.view_feedback, name="view_feedback"),






    # user urls

    path("login_code", views.login_code, name="login_code"),
    path("registration", views.registration, name="registration"),
    path("add_music_app", views.add_music_app, name="add_music_app"),
    path("feedback_app", views.feedback_app, name="feedback_app"),
    path("send_complaint_app", views.send_complaint_app, name="send_complaint_app"),
    path("reply_app", views.reply_app, name="reply_app"),
    path("music_app", views.music_app, name="music_app"),
    path("delete_app", views.delete_app, name="delete_app"),
    path("e_view_music", views.e_view_music, name="e_view_music"),
    path("edit_music", views.edit_music, name="edit_music"),



]
