<%@ Page Async="true" Title="" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="BusinessSignup.aspx.cs" Inherits="CapProj.BusinessSignup" %>
<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">

<h4>Business Signup</h4>

<div class="form-group">
    <hr />
    <asp:Label runat="server" AssociatedControlID="CompanyName" CssClass="col-md-2 control-label">Company Name</asp:Label>
    
    <div class="col-md-10">
        <asp:TextBox runat="server" ID="CompanyName" CssClass="form-control" TextMode="SingleLine" />
        <asp:RequiredFieldValidator runat="server" ControlToValidate="CompanyName"
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
    <asp:Label runat="server" AssociatedControlID="Special" CssClass="col-md-2 control-label">Any Special Instructions</asp:Label>
    
    <div class="col-md-10">
        <asp:TextBox runat="server" ID="Special" CssClass="form-control" TextMode="MultiLine" />
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
        <br />
        <div class="form-group">
            <div class="col-md-offset-2 col-md-10">
                <asp:Button runat="server" ID ="Register" OnClick ="RegisterButtonClick" Text="Register" CssClass="btn btn-default" />
            </div>
        </div>

</asp:Content>
