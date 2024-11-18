function limparCampos(){
    var form = document.getElementById("formCadastro");
    var inputs = form.querySelectorAll("input[type='text'], input[type='time'], input[type='checkbox']");

    inputs.forEach(function(input) {
        if (input.type === "checkbox") {
            input.checked = false; // Desmarca as caixas de seleção
        } else {
            input.value = ""; // Limpa os valores dos campos de texto e time
        }
    });
}

function cadastrarHemocentro(){
    var numeroCNPJ = document.getElementById("cnpj");
    var numeroTelefone = document.getElementById("telefone");

    if(numeroCNPJ.value.length != 18)
    {
        alert("CNPJ inválido.");
    }
    if(numeroTelefone.value.length != 15)
    {
        alert("Telefone inválido.");
    }
    validarSenha();
    if(preencherTodosOsCampos() == true)
    {
        //chamar rota de cadastro aqui
        alert('Cadastro realizado com sucesso!');
        limparCampos();
    }
} 

function validarCNPJ(campoCNPJ) {
    var numeroCNPJ = campoCNPJ.value.replace(/\D/g, "");

    if (numeroCNPJ.length > 14) {
        numeroCNPJ = numeroCNPJ.slice(0, 14);
    }

    campoCNPJ.value = numeroCNPJ
        .replace(/^(\d{2})(\d)/, "$1.$2")        
        .replace(/^(\d{2})\.(\d{3})(\d)/, "$1.$2.$3") 
        .replace(/\.(\d{3})(\d)/, ".$1/$2")    
        .replace(/(\d{4})(\d)/, "$1-$2");
}

function validarTelefone(campoTelefone) {
    var numeroTelefone = campoTelefone.value.replace(/\D/g, "");

    if (numeroTelefone.length > 11) {
        numeroTelefone = numeroTelefone.slice(0, 11);
    }

    campoTelefone.value = numeroTelefone
        .replace(/^(\d{2})(\d)/, "($1) $2")
        .replace(/(\d{5})(\d{4})$/, "$1-$2");
}

function validarTexto(){
    var form = document.getElementById("formCadastro");
    var inputs = form.querySelectorAll("input[type='text'], input[type='password']");
    inputs.forEach(element => {
        if(element.id == "cidade") {
            element.value = element.value.replace(/[^a-zA-Zá-úÁ-Ú\s]/g, "");  // Aceita letras e espaços
        }
        if(element.id == "estado") {
            element.value = element.value.replace(/[^a-zA-Zá-úÁ-Ú]/g, "");  // Remove números e caracteres especiais
            if (element.value.length > 2) {
                element.value = element.value.slice(0, 2);  // Limita a 2 caracteres
            }
        }
    });
}

function validarSenha(){
    var inputSenha = document.getElementById("senha");

    if(inputSenha.value.length < 5 || inputSenha.value.length > 20)
    {
        alert('Tamanho inválido de senha! Mínimo 5 caracteres, máximo 20');
    }
}

function preencherTodosOsCampos(){
    var form = document.getElementById("formCadastro");
    var inputs = form.querySelectorAll("[required]");
    var todosPreenchidos = true;

    inputs.forEach(function(input) {
        if (!input.value.trim()) { 
            input.classList.add("erro");
            todosPreenchidos = false;
        } else {
            input.classList.remove("erro"); 
        }
    });

    if (!todosPreenchidos) {
        alert("Por favor, preencha todos os campos obrigatórios.");
        return false; 
    }

    return true; 
}