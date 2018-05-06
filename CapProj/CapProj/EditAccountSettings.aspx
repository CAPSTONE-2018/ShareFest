<%@ Page Async="true" Title="" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="EditAccountSettings.aspx.cs" Inherits="CapProj.EditAccountSettings" %>
<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">

<h4>Edit Account Settings</h4>


<div class="form-group">
    <hr />
    <asp:Label runat="server" AssociatedControlID="CurrentUsername" CssClass="col-md-2 control-label">Current Username</asp:Label>
    
    <div class="col-md-10">
        <asp:TextBox runat="server" ID="CurrentUsername" CssClass="form-control" TextMode="SingleLine" />
        <asp:RequiredFieldValidator runat="server" ControlToValidate="CurrentUsername"
           CssClass="text-danger" ErrorMessage="This field is required." />
    </div>
</div>

<div class="form-group">
    <hr />
    <asp:Label runat="server" AssociatedControlID="CurrentPassword" CssClass="col-md-2 control-label">Current Password</asp:Label>
    
    <div class="col-md-10">
        <asp:TextBox runat="server" ID="CurrentPassword" CssClass="form-control" TextMode="SingleLine" />
        <asp:RequiredFieldValidator runat="server" ControlToValidate="CurrentPassword"
           CssClass="text-danger" ErrorMessage="This field is required." />
    </div>
</div>
<div class="form-group">
    <hr />
    <asp:Label runat="server" AssociatedControlID="FirstName" CssClass="col-md-2 control-label">First Name</asp:Label>
    
    <div class="col-md-10">
        <asp:TextBox runat="server" ID="FirstName" CssClass="form-control" TextMode="SingleLine" />
        <asp:RequiredFieldValidator runat="server" ControlToValidate="FirstName"
           CssClass="text-danger" ErrorMessage="This field is required." />
    </div>
</div>

<div class="form-group">
    <asp:Label runat="server" AssociatedControlID="LastName" CssClass="col-md-2 control-label">Last Name</asp:Label>
    
    <div class="col-md-10">
        <asp:TextBox runat="server" ID="LastName" CssClass="form-control" TextMode="SingleLine" />
        <asp:RequiredFieldValidator runat="server" ControlToValidate="LastName"
           CssClass="text-danger" ErrorMessage="This field is required." />
    </div>
</div>

<div class="form-group">
    <asp:Label runat="server" AssociatedControlID="NewUsername" CssClass="col-md-2 control-label">New Username</asp:Label>
    
    <div class="col-md-10">
        <asp:TextBox runat="server" ID="NewUsername" CssClass="form-control" TextMode="SingleLine" />
        <asp:RequiredFieldValidator runat="server" ControlToValidate="NewUsername"
           CssClass="text-danger" ErrorMessage="This field is required." />
    </div>
</div>

<div class="form-group">
    <asp:Label runat="server" AssociatedControlID="Email" CssClass="col-md-2 control-label">Email</asp:Label>
    
    <div class="col-md-10">
        <asp:TextBox runat="server" ID="Email" CssClass="form-control" TextMode="Email" />
        <asp:RequiredFieldValidator runat="server" ControlToValidate="Email"
           CssClass="text-danger" ErrorMessage="The email field is required." />
    </div>
</div>

<div class="form-group">
    <asp:Label runat="server" AssociatedControlID="Address" CssClass="col-md-2 control-label">Street Address</asp:Label>
    
    <div class="col-md-10">
        <asp:TextBox runat="server" ID="Address" CssClass="form-control" TextMode="SingleLine" />
        <asp:RequiredFieldValidator runat="server" ControlToValidate="Address"
           CssClass="text-danger" ErrorMessage="This field is required." />
    </div>
</div>

<div class="form-group">
    <asp:Label runat="server" AssociatedControlID="Zip" CssClass="col-md-2 control-label">Zip Code</asp:Label>
    
    <div class="col-md-10">
        <asp:TextBox runat="server" ID="Zip" CssClass="form-control" TextMode="SingleLine" />
        <asp:RequiredFieldValidator runat="server" ControlToValidate="Zip"
           CssClass="text-danger" ErrorMessage="This field is required." />
    </div>
</div>

<div class="form-group">
    <asp:Label runat="server" AssociatedControlID="Phone" CssClass="col-md-2 control-label">Phone Number</asp:Label>
    
    <div class="col-md-10">
        <asp:TextBox runat="server" ID="Phone" CssClass="form-control" TextMode="Phone" />
        <asp:RequiredFieldValidator runat="server" ControlToValidate="Phone"
           CssClass="text-danger" ErrorMessage="This field is required." />
    </div>
</div>

<a type="button" class="btn btn-primary" href="ChangePassword">Change Password Here</a>


<div class="form-group">
            <div class="col-md-offset-2 col-md-10">
                <asp:Button runat="server" ID="UpdateButton" OnClick="EditButtonClick" Text="Update Now" CssClass="btn btn-default" />
            </div>
</div>  


</asp:Content>
