from django import forms
import requests, json
from django.forms import ModelForm
from .models import Contact

class SMSForm(forms.Form):
    #here are the fields we'll be using to query the database 
    # for our contact model objects
    ## TODO ##
    # complete is pantry, #
    isPantry = forms.NullBooleanField(label='Send to Pantries', widget=forms.NullBooleanSelect)
    zip_code = forms.CharField(max_length=5, label='Zip', widget=forms.TextInput)
    body = forms.CharField(label='Message Body', widget=forms.Textarea)

class ContactForm(forms.ModelForm):
    class Meta:
        model = Contact
        fields = ('firstname','lastname','email','phonenumber','zipcode','isPantry')
        

def get_numbers(form):
    if form.is_valid():
        zipCode = form.cleaned_data.get('zip_code', '' )
        pantry = form.cleaned_data.get('isPantry', '')
    querryset = Contact.clients.filter(zipcode = zipCode).filter(isPantry = pantry)
    number_list = list()
    for item in querryset:
        number_list.append(str(item.phonenumber))
    return number_list

def get_all_numbers():
    querryset = Contact.clients.all()
    number_list = []
    for item in querryset:
        number_list.append(item.phonenumber)
    return number_list
