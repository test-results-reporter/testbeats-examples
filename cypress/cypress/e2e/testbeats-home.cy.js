describe('TestBeats Home Page Tests', () => {

  beforeEach(() => {
    // Visit the TestBeats home page before each test
    cy.visit('https://testbeats.com')
  })

  it('should have the correct title - PASS', () => {
    // This test will pass
    cy.title().should('include', 'TestBeats')
  })

  it('should have the correct title - FAIL', () => {
    // This test will fail intentionally
    cy.title().should('include', 'Wrong Title That Does Not Exist')
  })

})

