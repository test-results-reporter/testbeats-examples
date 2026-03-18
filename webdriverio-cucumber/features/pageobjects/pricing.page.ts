import Page from './page.js';

class PricingPage extends Page {
    public open () {
        return super.open('pricing');
    }
}

export default new PricingPage();
