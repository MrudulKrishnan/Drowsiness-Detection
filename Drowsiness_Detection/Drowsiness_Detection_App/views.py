from datetime import datetime

from django.contrib import auth
from django.contrib.auth.decorators import login_required
from django.core.files.storage import FileSystemStorage
from django.http import HttpResponse
from django.shortcuts import render, redirect
from Drowsiness_Detection_App.models import *

from django.core import serializers
import json
from django.http import JsonResponse

# Create your views here.


def login(request):
    return render(request, "login.html")


def logout(request):
    auth.logout(request)
    return render(request, "login.html")


def register(request):
    return render(request, "register.html")


def registered(request):
    firstname = request.POST["Firstname"]
    lastname = request.POST["Lastname"]
    gender = request.POST["Gender"]
    place = request.POST["Place"]
    post = request.POST["Post"]
    pin = request.POST["Pin"]
    email = request.POST["Email"]
    phone = request.POST["Phone"]
    vehicle_no = request.POST["VehicleNo"]
    username = request.POST["Username"]
    password = request.POST["Password"]
    login_obj = Login()
    login_obj.Username = username
    login_obj.Password = password
    login_obj.Type = 'user'
    login_obj.save()
    user_obj = User()
    user_obj.Firstname = firstname
    user_obj.Lastname = lastname
    user_obj.Gender = gender
    user_obj.Place = place
    user_obj.Post = post
    user_obj.Pin = pin
    user_obj.Email = email
    user_obj.Phone = phone
    user_obj.Vehicle = vehicle_no
    user_obj.LID = login_obj
    user_obj.save()
    return HttpResponse('''<script>alert ("registration successful");window.location="/"</script>''')


def login_fun(request):
    try:
        username = request.POST["Username"]
        password = request.POST["Password"]
        login_obj = Login.objects.get(Username=username, Password=password)
        if login_obj.Type == "admin":
            ob = auth.authenticate(username='admin', password='admin')
            if ob is not None:
                auth.login(request, ob)
            return redirect("admin_home")
        if login_obj.Type == "user":
            request.session['user_id'] = login_obj.id
            ob = auth.authenticate(username='admin', password='admin')
            if ob is not None:
                auth.login(request, ob)
            return redirect("user_home")
        if login_obj.Type == "block":
            return HttpResponse('''<script>alert ("sorry you are blocked!");window.location="/"</script>''')
    except:
        return HttpResponse('''<script>alert ("incorrect username or password");window.location="/"</script>''')


@login_required(login_url='/')
def admin_home(request):
    return render(request, "admin home.html")


@login_required(login_url='/')
def user_home(request):
    return render(request, "user home.html")


@login_required(login_url='/')
def block_unblock(request):
    user_obj = User.objects.all()
    return render(request, "block and unblock user.html", {'user_obj': user_obj})


@login_required(login_url='/')
def block(request, block_id):
    user_obj = User.objects.get(id=block_id)
    login_obj = Login.objects.get(id=user_obj.LID.id)
    login_obj.Type = "block"
    login_obj.save()
    return HttpResponse('''<script>alert ("blocked");window.location="/block_unblock"</script>''')


@login_required(login_url='/')
def unblock(request, unblock_id):
    user_obj = User.objects.get(id=unblock_id)
    login_obj = Login.objects.get(id=user_obj.LID.id)
    login_obj.Type = "user"
    login_obj.save()
    return HttpResponse('''<script>alert ("unblocked");window.location="/block_unblock"</script>''')


@login_required(login_url='/')
def add_manage_music(request):
    music_obj = Music.objects.all()
    return render(request, "add and manage music.html", {'music_obj': music_obj})


@login_required(login_url='/')
def add_music(request):
    return render(request, "add new.html")


@login_required(login_url='/')
def adding_musics(request):
    music = request.FILES["Music_files"]
    details = request.POST["Details"]
    emotions = request.POST["select_emotions"]
    music_obj = Music()
    music_obj.Musics = music
    music_obj.Details = details
    music_obj.Emotions = emotions
    music_obj.Date = datetime.now()
    music_obj.save()
    return HttpResponse('''<script>alert ("music added successfully");window.location="/add_manage_music"</script>''')


@login_required(login_url='/')
def delete(request, delete_id):
    music_obj = Music.objects.get(id=delete_id)
    music_obj.delete()
    return HttpResponse('''<script>alert ("music deleted");window.location="/add_manage_music"</script>''')


@login_required(login_url='/')
def complaints_reply_admin(request):
    complain_obj = Complaints.objects.all()
    return render(request, "view complaint and send reply.html", {'complaint_obj': complain_obj})


@login_required(login_url='/')
def reply_form(request, reply_id):
    request.session['reply_id'] = reply_id
    return render(request, "send reply.html")


@login_required(login_url='/')
def send_reply(request):
    reply = request.POST["Reply"]
    # obj_id = request.session['reply_id']
    reply_obj = Complaints.objects.get(id=request.session['reply_id'])
    reply_obj.Reply = reply
    reply_obj.save()
    return HttpResponse('''<script>alert ("Reply sent successfully");
                        window.location="/complaints_reply_admin"</script>''')


@login_required(login_url='/')
def view_feedback(request):
    feedback_obj = Feedback.objects.all()
    return render(request, "view feedback.html", {'feedback_obj': feedback_obj})

# ///////////////////////////////////////////////////////////////webservice/////////////////////////////////////////


def login_code(request):
    username = request.POST['uname']
    password = request.POST['pass']
    try:
        users = Login.objects.get(Username=username, Password=password)
        if users is None:
            data = {"task": "invalid"}

        else:
            data = {"task": "valid", "id": users.id}
            r = json.dumps(data)
            return HttpResponse(r)
    except:
        data = {"task": "invalid"}
        r = json.dumps(data)
        print(r)
        return HttpResponse(r)


def registration(request):
    firstname = request.POST['Firstname']
    lastname = request.POST['Lastname']
    place = request.POST['Place']
    post_office = request.POST['Post']
    pin_code = request.POST['Pin']
    phone = request.POST['Phone']
    gender = request.POST['Gender']
    vehicle = request.POST['Vehicle']
    email_id = request.POST['Email']
    username = request.POST['Username']
    password = request.POST['Password']

    lob = Login()
    lob.Username = username
    lob.Password = password
    lob.Type = 'user'
    lob.save()

    user_obj = User()
    user_obj.Firstname = firstname
    user_obj.Lastname = lastname
    user_obj.Place = place
    user_obj.Post = post_office
    user_obj.Pin = pin_code
    user_obj.Phone = phone
    user_obj.Gender = gender
    user_obj.Vehicle = vehicle
    user_obj.Email = email_id
    user_obj.LID = lob
    user_obj.save()
    data = {"task": "success"}
    r = json.dumps(data)
    return HttpResponse(r)


def add_music_app(request):
    music = request.FILES["file"]
    fs = FileSystemStorage()
    fn = fs.save(music.name, music)
    details = request.POST["Details"]
    emotions = request.POST["Emotions"]
    music_obj = Music()
    music_obj.Musics = fn
    music_obj.Details = details
    music_obj.Emotions = emotions
    music_obj.Date = datetime.now()
    music_obj.save()
    data = {"task": "success"}
    r = json.dumps(data)
    return HttpResponse(r)


def feedback_app(request):
    feedback = request.POST["Feedback"]
    feedback_id = request.POST["lid"]
    feedback_obj = Feedback()
    feedback_obj.Feedbacks = feedback
    feedback_obj.Date = datetime.now()
    feedback_obj.UID = User.objects.get(LID__id=feedback_id)
    feedback_obj.save()
    data = {'task': "success"}
    r = json.dumps(data)
    return HttpResponse(r)


def send_complaint_app(request):
    complaints = request.POST["Complaint"]
    u_id = request.POST["uid"]
    date = datetime.now()
    reply = "waiting"
    complaint_obj = Complaints()
    complaint_obj.Complaint = complaints
    complaint_obj.Date = date
    complaint_obj.Reply = reply
    complaint_obj.UID = User.objects.get(LID__id=u_id)
    complaint_obj.save()
    data = {'task': 'success'}
    r = json.dumps(data)
    return HttpResponse(r)


def reply_app(request):
    user_id = request.POST['lid']
    complaint_obj = Complaints.objects.filter(UID__LID__id=user_id)
    data = []
    for i in complaint_obj:
        row = {'Complaint': i.Complaint, 'Reply': i.Reply, 'Date': str(i.Date)}
        data.append(row)
    r = json.dumps(data)
    return HttpResponse(r)


def music_app(request):
    music_obj = Music.objects.all()
    music_datas = []

    for i in music_obj:
        data = {'Musics': str(i.Musics.url), 'Details': i.Details, 'Emotions': i.Emotions, 'mid': i.id}
        music_datas.append(data)

    r = json.dumps(music_datas)
    return HttpResponse(r)


def e_view_music(request):
    mid = request.POST['mid']
    music_obj = Music.objects.filter(id=mid)
    music_datas = []

    for i in music_obj:
        data = {'Musics': str(i.Musics), 'Details': i.Details, 'Emotions': i.Emotions, 'mid': i.id}
        music_datas.append(data)

    r = json.dumps(music_datas)
    return HttpResponse(r)


def edit_music(request):
    music = request.FILES["file"]
    fs = FileSystemStorage()
    fn = fs.save(music.name, music)
    details = request.POST["Details"]
    emotions = request.POST["Emotions"]
    music_obj = Music()
    music_obj.Musics = fn
    music_obj.Details = details
    music_obj.Emotions = emotions
    music_obj.Date = datetime.now()
    music_obj.save()
    data = {"task": "success"}
    r = json.dumps(data)
    return HttpResponse(r)


def delete_app(request,):
    music_id = request.POST["m_id"]
    music_obj = Music.objects.get(id=music_id)
    music_obj.delete()
    data = {"task": "success"}
    r = json.dumps(data)
    return HttpResponse(r)

