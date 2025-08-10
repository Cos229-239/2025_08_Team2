defmodule Backend.Accounts.UserNotifier do
  import Swoosh.Email

  alias Backend.Mailer

  # Delivers the email using the application mailer.
  defp deliver(recipient, subject, html_body, text_body) do
    email =
      new()
      |> to(recipient)
      |> from({"Backend", "contact@example.com"})
      |> subject(subject)
      |> html_body(html_body)
      |> text_body(text_body)

    with {:ok, _metadata} <- Mailer.deliver(email) do
      {:ok, email}
    end
  end

  @doc """
  Deliver instructions to confirm account.
  """
  def deliver_confirmation_instructions(user, url) do
    html_body = """
    <div style="font-family: system-ui, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px;">
      <h1 style="font-size: 24px; color: #1a1a1a;">Confirm Your Account</h1>
      <p style="color: #333; line-height: 1.5;">Thanks for signing up! Please confirm your account by clicking the link below:</p>
      <p style="margin: 25px 0;">
        <a href="#{url}" style="background: #1a1a1a; color: white; padding: 10px 20px; border-radius: 5px; text-decoration: none;">Confirm Account</a>
      </p>
      <p style="color: #666; font-size: 14px;">Or visit: #{url}</p>
      <p style="color: #333; margin-top: 30px;">Best,<br>Backend Team</p>
    </div>
    """

    deliver(user.email, html_body, "Confirmation instructions", """

    ==============================

    Hi #{user.email},

    You can confirm your account by visiting the URL below:

    #{url}

    If you didn't create an account with us, please ignore this.

    ==============================
    """)
  end

  @doc """
  Deliver instructions to reset a user password.
  """
  def deliver_reset_password_instructions(user, url) do
    html_body = """
    <div style="font-family: system-ui, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px;">
      <h1 style="font-size: 24px; color: #1a1a1a;">Reset Your Password</h1>
      <p style="color: #333; line-height: 1.5;">You can reset your password by clicking the link below:</p>
      <p style="margin: 25px 0;">
        <a href="#{url}" style="background: #1a1a1a; color: white; padding: 10px 20px; border-radius: 5px; text-decoration: none;">Reset Password</a>
      </p>
      <p style="color: #666; font-size: 14px;">Or visit: #{url}</p>
      <p style="color: #333; margin-top: 30px;">Best,<br>Backend Team</p>
    </div>
    """

    deliver(user.email, html_body, "Reset password instructions", """

    ==============================

    Hi #{user.email},

    You can reset your password by visiting the URL below:

    #{url}

    If you didn't request this change, please ignore this.

    ==============================
    """)
  end

  @doc """
  Deliver instructions to update a user email.
  """
  def deliver_update_email_instructions(user, url) do
    html_body = """
    <div style="font-family: system-ui, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px;">
      <h1 style="font-size: 24px; color: #1a1a1a;">Update Your Email</h1>
      <p style="color: #333; line-height: 1.5;">You can change your email by clicking the link below:</p>
      <p style="margin: 25px 0;">
        <a href="#{url}" style="background: #1a1a1a; color: white; padding: 10px 20px; border-radius: 5px; text-decoration: none;">Update Email</a>
      </p>
      <p style="color: #666; font-size: 14px;">Or visit: #{url}</p>
      <p style="color: #333; margin-top: 30px;">Best,<br>Backend Team</p>
    </div>
    """

    deliver(user.email, html_body, "Update email instructions", """

    ==============================

    Hi #{user.email},

    You can change your email by visiting the URL below:

    #{url}

    If you didn't request this change, please ignore this.

    ==============================
    """)
  end

  def deliver_oauth_welcome_message(user) do
    html_body = """
    <div style="font-family: system-ui, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px;">
      <h1 style="font-size: 24px; color: #1a1a1a;">Welcome to Ravend Gaming News! ✨</h1>
      <p style="color: #333; line-height: 1.5;">Thanks for signing up with Google. Your account is verified and ready to go.</p>
      <p style="margin: 25px 0;">
        <a href="http://localhost:4000" style="background: #1a1a1a; color: white; padding: 10px 20px; border-radius: 5px; text-decoration: none;">Start Using Raven Gaming</a>
      </p>
      <p style="color: #666; font-size: 14px;">Or visit: http://localhost:4000</p>
      <p style="color: #333; margin-top: 30px;">Best,<br>Raven Gaming Team</p>
    </div>
    """

    text_body = """
    Welcome to Raven Gaming News! ✨

    Thanks for signing up with Google. Your account is verified and ready to go.

    Start using Raven Gaming News at: http://localhost:4000

    Best,
    Raven Gaming Team
    """

    deliver(user.email, "Welcome to Raven Gaming News! ✨", html_body, text_body)
  end
end
