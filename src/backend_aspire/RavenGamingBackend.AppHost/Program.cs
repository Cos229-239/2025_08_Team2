var builder = DistributedApplication.CreateBuilder(args);

var dbService = builder.AddPostgres("postgres").WithPgAdmin(p => p.WithHostPort(5050)).AddDatabase("aspiredb");

var apiService = builder.AddProject<Projects.RavenGamingBackend_ApiService>("apiservice").WithReference(dbService);

builder.AddProject<Projects.RavenGamingBackend_Web>("webfrontend")
    .WithExternalHttpEndpoints()
    .WithReference(apiService);

builder.Build().Run();
