# Raven Gaming Backend

All development is done inside a `devcontainer`.  So there is no need to install and other things besides VS Code and Docker Desktop.

## Prerequisites

Open Windows Terminal / PowerShell (or download from the website if terminals are scary for you.)

Install [VS Code](https://code.visualstudio.com/) if you do not already have it.

```powershell
winget install Microsoft.VisualStudioCode
```

Install [Docker Desktop](https://www.docker.com/products/docker-desktop/) if you do not already have it.

```powershell
winget install Docker.DockerDesktop
```

### Database

Postgres database containers should be running with defaults
Check the dev.exs for the latest:

```elixir
  username: "postgres",
  password: "postgres",
  hostname: "db",
  database: "postgres",
```

### Building and Running

#### Secrets

First you must add a /config/env.exs file.
Add this lines which is the secret that goes with the GOOGLE_CLIENT_ID in the `devcontainer.json`:

```elixir
System.put_env("GOOGLE_CLIENT_SECRET", "ask-mel-for-your-secret")
```

#### Running the backend service

1. Clone the repo
2. Open the `backend` folder in VS Code.
3. Before building the dev container, create the ./config/env.exs file (for the google client secret.)  Ask Mel for your secret.
4. Connect to the devcontainer.
5. run `mix setup` to download dependencies.
6. run `mix compile`
7. run to start the services `mix phx.server`
7. Open localhost:4000 to browse and that is the base url for the API as well for the android client to use to fetch data.

#### Stopping the backend service

1. In the terminal where you started the service, hit `CTRL-C` twice to stop the server. (Or `CTRL-C` then `a`);