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
    var nome = document.getElementById("nomeHemocentro").value;
    var numeroCNPJ = document.getElementById("cnpj").value;
    var rua = document.getElementById("rua").value;
    var numero = document.getElementById("numero").value;
    var bairro = document.getElementById("bairro").value;
    var cep = document.getElementById("cep").value;
    var cidade = document.getElementById("cidade").value;
    var estado = document.getElementById("estado").value;
    var numeroTelefone = document.getElementById("telefone").value;
    var email = document.getElementById("emailRep").value;
    var senha = document.getElementById("senha").value;

    validarSenha();
    if(preencherTodosOsCampos() == true)
    {
        var hemocentroData = {
            nome: nome,
            cnpj: numeroCNPJ,
            rua: rua,
            numero: numero,
            bairro: bairro,
            cep: cep,
            cidade: cidade,
            estado: estado,
            telefone: numeroTelefone,
            email: email,
            senha: senha // Só inclua senha se for realmente necessário para o backend
        };

        fetch("http://localhost:8080/blood-centers", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(hemocentroData)
        })
        .then(response => {
            if (response.ok) {
                alert("Cadastro realizado com sucesso!");
                limparCampos();
            } else {
                response.json().then(err => {
                    alert("Erro ao cadastrar hemocentro: " + err.message);
                });
            }
        })
        .catch(error => {
            console.error("Erro na requisição:", error);
            alert("Erro ao conectar com o servidor.");
        });
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

function validarCEP(campoCEP) {
    var numeroCEP = campoCEP.value.replace(/\D/g, "");

   if (numeroCEP.length > 8) {
    numeroCEP = numeroCEP.slice(0, 8);
    }

    campoCEP.value = numeroCEP.replace(/(\d{5})(\d)/, "$1-$2");
}

function validarNumero(campoNumero) {
    var numero = campoNumero.value.replace(/\D/g, "");

    if (numero.length > 5) {
        numero = numero.slice(0, 14);
    }

    campoNumero.value = numero
        .replace(/\D/g, "")
        .substring(0, 5);
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