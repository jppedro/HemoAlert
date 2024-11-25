document.addEventListener('DOMContentLoaded', () => {
    const loginButton = document.querySelector('.button-login');
    const emailField = document.querySelector('input[type="email"]');
    const passwordField = document.querySelector('input[type="password"]');
    const registerLink = document.querySelector('.register-link');

    loginButton.addEventListener('click', (event) => {
      event.preventDefault();
  
      const email = emailField.value.trim();
      const password = passwordField.value.trim();
  
      if (!email || !password) {
        alert('Por favor, preencha todos os campos.');
        return;
      }

      if (email !== 'teste@teste.com' || password !== 'teste') {
        alert('E-mail ou senha incorretos. Tente novamente.');
        return;
      }
  
      alert('Login realizado com sucesso!');
      window.location.href = '../Alerta/alerta.html';
    });
  
    registerLink.addEventListener('click', (event) => {
      event.preventDefault(); 
      window.location.href = '../Cadastro/cadastro.html';
    });
  });
  