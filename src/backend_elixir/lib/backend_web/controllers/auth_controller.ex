defmodule BackendWeb.AuthController do
  use BackendWeb, :controller
  alias Backend.Accounts
  alias BackendWeb.UserAuth
  require Logger
  plug Ueberauth

  def request(conn, _params) do
    Phoenix.Controller.redirect(conn, to: Ueberauth.Strategy.Helpers.callback_url(conn))
  end

  def callback(%{assigns: %{ueberauth_auth: auth}} = conn, _params) do
    email = auth.info.email
    provider_string = Atom.to_string(auth.provider)
    # Log the auth info for debugging
    dbg("Ueberauth auth info: #{inspect(auth)}")

    case Accounts.get_user_by_email(email) do
      nil ->
        # User does not exist, so create a new user
        user_params = %{
          email: email,
          name: auth.info.name,
          provider: provider_string,
          token: auth.credentials.token,
          avatar_url: auth.info.image
        }

        case Accounts.register_oauth_user(user_params) do
          {:ok, user} ->
            UserAuth.log_in_user(conn, user)

          {:error, changeset} ->
            Logger.error("Failed to create user #{inspect(changeset)}.")

            conn
            |> put_flash(:error, "Failed to create user.")
            |> redirect(to: ~p"/")
        end

      user ->
        # User exists, update session or other details if necessary
        UserAuth.log_in_user(conn, user)
    end
  end
end
