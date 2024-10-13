import { expect, type Locator, type Page } from "@playwright/test";
import { UserData } from "../lib/helpers/generateUserData";

export class AeCartPage {
  readonly page: Page;
  readonly userData: UserData;

  readonly checkoutButton: Locator;
  readonly checkoutRegisterLoginButton: Locator;
  readonly checkoutComment: Locator;
  readonly cartNavBar: Locator;
  readonly placeOrderLink: Locator;

  constructor(page: Page, userData: UserData) {
    this.page = page;
    this.userData = userData;

    this.checkoutButton = page.getByText("Proceed To Checkout");
    this.checkoutRegisterLoginButton = page.getByRole("link", {
      name: "Register / Login",
    });
    this.cartNavBar = page.getByRole("link", { name: "Cart" });
    this.checkoutComment = page.locator('textarea[name="message"]');
    this.placeOrderLink = page.getByRole("link", { name: "Place Order" });
  }

  async clickCheckoutButton() {
    await this.checkoutButton.click();
  }

  async clickCheckoutRegisterLoginButton() {
    await this.checkoutRegisterLoginButton.click();
  }

  async clickCartNavBar() {
    await this.cartNavBar.click();
  }

  async addComment() {
    await this.checkoutComment.fill(this.userData.comment).catch((error) => {
      console.error("Failed to enter comment:", error);
    });
  }

  async placeOrder() {
    await this.placeOrderLink.click();
  }
}
