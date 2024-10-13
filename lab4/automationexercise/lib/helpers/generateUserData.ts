import { faker } from "@faker-js/faker";

export interface UserData {
  firstName: string;
  lastName: string;
  fullName: string;
  email: string;
  password: string;
  day: number;
  month: string;
  year: number;
  company: string;
  address: string;
  country: number;
  state: string;
  city: string;
  zipCode: string;
  mobileNumber: string;
  comment: string;
  creditCardNumber: string;
  creditCardCVC: string;
  creditCardExpirationMonth: string;
  creditCardExpirationYear: string;
  contactSubject: string;
  contactMessage: string;
}

export function generateUserData(): UserData {
  const firstName = faker.person.firstName();
  const lastName = faker.person.lastName();
  const fullName = `${firstName} ${lastName}`;
  const email = faker.internet.email();
  const password = faker.internet.password();
  const day = faker.number.int({ min: 1, max: 31 });
  const month = faker.number.int({ min: 1, max: 12 }).toString();
  const currentYear = new Date().getFullYear();
  const year = currentYear - faker.number.int({ min: 18, max: 100 });
  const company = faker.company.name();
  const address = faker.location.streetAddress({ useFullAddress: true });
  const country = faker.number.int({ min: 1, max: 7 });
  const state = faker.location.state();
  const city = faker.location.city();
  const zipCode = faker.location.zipCode();
  const mobileNumber = faker.phone.number();

  const comment = faker.lorem.paragraph();
  const creditCardNumber = faker.finance.creditCardNumber();
  const contactSubject = faker.lorem.sentence(4);
  const contactMessage = faker.lorem.paragraph();
  const creditCardCVC = faker.finance.creditCardCVV();
  const creditCardExpirationMonth = month.padStart(2, "0");
  const creditCardExpirationYear = (
    currentYear + faker.number.int({ min: 1, max: 10 })
  ).toString();

  return {
    firstName,
    lastName,
    fullName,
    email,
    password,
    day,
    month,
    year,
    company,
    address,
    country,
    state,
    city,
    zipCode,
    mobileNumber,
    comment,
    creditCardNumber,
    contactSubject,
    contactMessage,
    creditCardCVC,
    creditCardExpirationMonth,
    creditCardExpirationYear,
  };
}
