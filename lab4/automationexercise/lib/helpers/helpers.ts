import { expect, type Locator, type Page } from "@playwright/test";
import { UserData } from "./generateUserData";

export class Helpers {
  readonly page: Page;
  readonly userData: UserData;

  readonly logoutLink: Locator;
  readonly loginLink: Locator;
  readonly contactUsLink: Locator;

  readonly loginEmail: Locator;
  readonly loginPassword: Locator;
  readonly loginButton: Locator;
  readonly incorrectLoginError: Locator;

  constructor(page: Page, userData: UserData) {
    this.page = page;
    this.userData = userData;

    this.logoutLink = page.getByRole("link", { name: "Logout" });
    this.loginLink = page.getByRole("link", { name: "Signup / Login" });
    this.contactUsLink = page.getByRole("link", { name: "Contact us" });

    this.loginEmail = page.locator('[data-qa="login-email"]');
    this.loginPassword = page.locator('[data-qa="login-password"]');
    this.loginButton = page.locator('[data-qa="login-button"]');
    this.incorrectLoginError = page.getByText(
      "Your email or password is incorrect"
    );
  }

  async goto(url: string = "/") {
    await this.page.goto(url);
  }

  async clickLogoutLink() {
    await this.logoutLink.click();
    await this.userIsLoggedOut();
  }

  async userIsLoggedOut() {
    await expect(this.loginLink).toBeVisible();
  }

  async clickLoginLink() {
    await this.loginLink.click();
  }

  async clickContactUsLink() {
    await this.contactUsLink.click();
  }

  async handleDialog() {
    this.page.on("dialog", async (dialog) => {
      console.log(`Dialog message: ${dialog.message()}`);
      await dialog.accept();
    });
  }

  async login() {
    await this.fillLoginForm();
    await this.clickLoginButton();
  }

  async fillLoginForm() {
    await this.loginEmail.fill(this.userData.email);
    await this.loginPassword.fill(this.userData.password);
  }

  async clickLoginButton() {
    await this.loginButton.click();
    await this.incorrectLogin();
  }

  async incorrectLogin() {
    if (await this.incorrectLoginError.isVisible()) {
      console.log("email or password is incorrect");
    }
  }
}
