<%@ Page Title="" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="EditAccountSettings.aspx.cs" Inherits="CapProj.EditAccountSettings" %>
<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">

<h4>Edit Account Settings</h4>

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
    <asp:Label runat="server" AssociatedControlID="Username" CssClass="col-md-2 control-label">Username</asp:Label>
    
    <div class="col-md-10">
        <asp:TextBox runat="server" ID="Username" CssClass="form-control" TextMode="SingleLine" />
        <asp:RequiredFieldValidator runat="server" ControlToValidate="Username"
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

<%--- Here you will have to change the button function to go to whatever SetUserInfo function you end up implementing

<div class="form-group">
            <div class="col-md-offset-2 col-md-10">
                <asp:Button runat="server" ID="Register" OnClick="RegisterBtn_Click" Text="Register" CssClass="btn btn-default" />
            </div>
</div>   --%>


</asp:Content>
