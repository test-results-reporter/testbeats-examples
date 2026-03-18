import Page from './page.js';

class HomePage extends Page {
    public open () {
        return super.open('');
    }
}

export default new HomePage();
