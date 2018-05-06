<%@ Page Async="true" Title="" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="ChangePassword.aspx.cs" Inherits="CapProj.ChangePassword" %>
<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">

    <h4>Change Password</h4>


<div class="form-group">
                        <asp:Label runat="server" AssociatedControlID="Username" CssClass="col-md-2 control-label">Username</asp:Label>
                        <div class="col-md-10">
                            <asp:TextBox runat="server" ID="Username" TextMode="SingleLine" CssClass="form-control" />
                            <asp:RequiredFieldValidator runat="server" ControlToValidate="Username" CssClass="text-danger" ErrorMessage="This field is required." />
                        </div>
                    </div>

<div class="form-group">
                        <asp:Label runat="server" AssociatedControlID="CurrentPassword" CssClass="col-md-2 control-label">Current Password</asp:Label>
                        <div class="col-md-10">
                            <asp:TextBox runat="server" ID="CurrentPassword" TextMode="Password" CssClass="form-control" />
                            <asp:RequiredFieldValidator runat="server" ControlToValidate="CurrentPassword" CssClass="text-danger" ErrorMessage="The password field is required." />
                        </div>
                    </div>

<div class="form-group">
            <asp:Label runat="server" AssociatedControlID="NewPassword" CssClass="col-md-2 control-label">New Password</asp:Label>
            <div class="col-md-10">
                <asp:TextBox runat="server" ID="NewPassword" TextMode="Password" CssClass="form-control" />
                <asp:RequiredFieldValidator runat="server" ControlToValidate="NewPassword"
                    CssClass="text-danger" ErrorMessage="The password field is required." />
            </div>
        </div>
       <div class="form-group">
            <asp:Label runat="server" AssociatedControlID="ConfirmPassword" CssClass="col-md-2 control-label">Confirm New Password</asp:Label>
            <div class="col-md-10">
                <asp:TextBox runat="server" ID="ConfirmPassword" TextMode="Password" CssClass="form-control" />
                <asp:RequiredFieldValidator runat="server" ControlToValidate="ConfirmPassword"
                    CssClass="text-danger" Display="Dynamic" ErrorMessage="The confirm password field is required." />
                <asp:CompareValidator runat="server" ControlToCompare="NewPassword" ControlToValidate="ConfirmPassword"
                    CssClass="text-danger" Display="Dynamic" ErrorMessage="The password and confirmation password do not match." />
            </div>
        </div>

 <div class="form-group">
            <div class="col-md-offset-2 col-md-10">
                <asp:Button runat="server" ID ="Register" OnClick ="ChangePWButtonClick" Text="Update" CssClass="btn btn-default" />
            </div>
        </div> 
</asp:Content>
