from django.urls import path
from . import views
urlpatterns = [
    path('', views.index, name='index'),
    path('sign-up/', views.sign_up, name='sign-up'),
    path('compose/', views.compose, name='compose'),
    path('upload/', views.upload, name='upload'),
    path('result/', views.result, name='result')
]