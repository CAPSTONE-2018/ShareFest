
The API server uses
.NET Core 2.0 rather than .NET Framework. The .NET Core 2.0 is cross-platform
(and open-source) so it should be able to run on our Linux server.

However, this does not apply to the Web server currently since it does use .NET
Framework. Porting to ASP.NET Core appears to be possible, but it
would require changes. For example, the current project uses ASP.NET
Web Forms (.aspx) while ASP.NET Core 2.0 seems to only support Razor
Pages (.cshtml).
