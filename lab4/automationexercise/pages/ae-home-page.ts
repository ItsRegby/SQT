import { expect, type Locator, type Page } from "@playwright/test";

export class AeHomePage {
  readonly page: Page;
  readonly productNumbers: Locator;

  constructor(page: Page) {
    this.page = page;
    this.productNumbers = page.locator('a[href*="product_details"]');
  }

  /**
   * Navigates to the specified URL or the base URL if no URL is provided.
   * @param url
   */
  async goto(url: string = "/") {
    await this.page.goto(url);
  }

  async scrolldownHalfwayDownPage() {
    await this.page.evaluate(() => {
      window.scrollTo(0, document.body.scrollHeight / 2);
    });
  }

  async selectProduct() {
    const count = await this.productNumbers.count();
    const randomIndex = Math.floor(Math.random() * count);
    console.log(`Selecting product number ${randomIndex}`);
    await this.productNumbers.nth(randomIndex).click();
    await this.avoidGoogleVignette();
  }

  async avoidGoogleVignette() {
    if (this.page.url().includes("#google_vignette")) {
      console.log(
        "Google Vignette detected. Going back and selecting product again."
      );
      await this.page.goBack();
      await this.selectProduct();
    }
  }
}
