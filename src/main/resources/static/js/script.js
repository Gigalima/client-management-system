$('document').ready(function(){
	$('.table #deleteButton').on('click',function(event){
    	event.preventDefault();
    	var href = $(this).attr('href');
    	$('#deleteModal #delRef').attr('href', href);
    	$('#deleteModal').modal();
    });
    setTimeout(function() {
        var mensagemSucesso = document.getElementById("mensagemSucesso");
        mensagemSucesso.parentNode.removeChild(mensagemSucesso);
    }, 3000);

    setTimeout(function() {
        var atualizacaoSucesso = document.getElementById("atualizacaoSucesso");
        atualizacaoSucesso.parentNode.removeChild(atualizacaoSucesso);
    }, 3000);

    setTimeout(function() {
        var cadastroSucesso = document.getElementById("cadastroSucesso");
        cadastroSucesso.parentNode.removeChild(cadastroSucesso);
    }, 3000);

});

function validarCPF(input) {
            var cpf = input.value.replace(/\D/g, '');

            cpf = cpf.replace(/(\d{3})(\d)/, '$1.$2');
            cpf = cpf.replace(/(\d{3})(\d)/, '$1.$2');
            cpf = cpf.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
            input.value = cpf;

            if (cpf.length >= 14) {
                input.value = cpf.substring(0, 14);
            }
        }

function validarTelefone(input) {
            var telefone = input.value.replace(/\D/g, '');

            if (telefone.length <= 10) {
                telefone = telefone.replace(/^(\d{2})(\d{4})(\d{4})$/, '($1) $2-$3');
            } else {
                telefone = telefone.replace(/^(\d{2})(\d{5})(\d{4})$/, '($1) $2-$3');
            }

            input.value = telefone;

            if (telefone.length >= 15) {
                input.value = telefone.substring(0, 15);
            }
        }