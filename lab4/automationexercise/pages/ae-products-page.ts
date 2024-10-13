import { expect, type Locator, type Page } from "@playwright/test";

export class AeProductsPage {
  readonly page: Page;
  readonly productQuantity: Locator;
  readonly addToCartButton: Locator;
  readonly viewCartLink: Locator;

  constructor(page: Page) {
    this.page = page;
    this.productQuantity = page.locator("#quantity");
    this.addToCartButton = page.getByRole("button", { name: " Add to cart" });
    this.viewCartLink = page.getByRole("link", { name: "View Cart" });
  }

  async selectProduct_quantity(productQuantity: number) {
    await this.productQuantity.fill(productQuantity.toString());
  }

  async addProduct_ToCart() {
    await this.addToCartButton.click();
    await this.viewCartLink.click();
  }
}
