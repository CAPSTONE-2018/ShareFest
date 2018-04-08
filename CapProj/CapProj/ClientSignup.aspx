<%@ Page Title="" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="ClientSignup.aspx.cs" Inherits="CapProj.ClientSignup" %>
<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
<h2>Client Signup</h2>

<div class="form-group">
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

<div class="form-group">
            <asp:Label runat="server" AssociatedControlID="Password" CssClass="col-md-2 control-label">Password</asp:Label>
            <div class="col-md-10">
                <asp:TextBox runat="server" ID="Password" TextMode="Password" CssClass="form-control" />
                <asp:RequiredFieldValidator runat="server" ControlToValidate="Password"
                    CssClass="text-danger" ErrorMessage="The password field is required." />
            </div>
        </div>
        <div class="form-group">
            <asp:Label runat="server" AssociatedControlID="ConfirmPassword" CssClass="col-md-2 control-label">Confirm password</asp:Label>
            <div class="col-md-10">
                <asp:TextBox runat="server" ID="ConfirmPassword" TextMode="Password" CssClass="form-control" />
                <asp:RequiredFieldValidator runat="server" ControlToValidate="ConfirmPassword"
                    CssClass="text-danger" Display="Dynamic" ErrorMessage="The confirm password field is required." />
                <asp:CompareValidator runat="server" ControlToCompare="Password" ControlToValidate="ConfirmPassword"
                    CssClass="text-danger" Display="Dynamic" ErrorMessage="The password and confirmation password do not match." />
            </div>
        </div>
        <div class="form-group">
            <div class="col-md-offset-2 col-md-10">
                <asp:Button runat="server" Text="Register" CssClass="btn btn-default" />
            </div>
        </div>
</asp:Content>
