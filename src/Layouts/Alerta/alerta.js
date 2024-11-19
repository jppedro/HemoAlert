const tiposSangueInput = document.getElementById("tipos-sangue");
const mensagemTextarea = document.getElementById("mensagem");
const confirmarButton = document.getElementById("confirmar-envio");

confirmarButton.addEventListener("click", () => {
    const tiposSangue = tiposSangueInput.value.trim();
    const mensagem = mensagemTextarea.value.trim();

    if (!tiposSangue) {
        alert("Por favor, insira os tipos sanguíneos com maior urgência.");
        return;
    }

    if (!mensagem) {
        alert("Por favor, personalize a mensagem ou deixe o texto padrão.");
        return;
    }

    const confirmacao = confirm(
        `Confirma o envio do seguinte alerta?\n\n` +
        `Tipos sanguíneos: ${tiposSangue}\n` +
        `Mensagem: ${mensagem}`
    );

    if (confirmacao) {
        alert("Alerta enviado com sucesso!");
        tiposSangueInput.value = "";
        mensagemTextarea.value = "";
    }
});
