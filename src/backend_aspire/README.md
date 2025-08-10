# C# Backend

This repository contains the C# backend for the Raven Gaming project, which is built using the Aspire framework. The backend is responsible for handling various functionalities such as user management, news management, and providing a web API for the frontend to interact with.

# Prerequisites
- Docker Desktop installed for devcontainer.
- Visual Studio Code installed.

# Getting Started

1. Close any previous folders in Visual Studio Code.
2. Open this folder ./src/backend_aspire in Visual Studio Code.
3. You should be prompted to reopen in a devcontainer. Click "Reopen in Container".
4. Wait for the devcontainer to build and start.
5. Once the devcontainer is ready, you can start working on the backend code.

The container should have all the necessary dependencies installed, including the Aspire framework and any other required libraries.

To run the backend, you can use the following command in the terminal:

```bash
dotnet build
cd RavenGamingBackend.AppHost
dotnet run
```

In the terminal you should see the aspire dashboard URL, which you can open in your browser to access the Aspire dashboard.
Look for https://localhost:17286/login?t=<random_token> in the terminal output.


