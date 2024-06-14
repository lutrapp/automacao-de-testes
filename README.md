# Test Automation Portal
Projeto de Suporte aos Alunos do curso de automação de testes fullstack do professor Tiago Samuel Rodrigues Teixeira. Com esse projeto é possível criar testes unitários, integrados, de sistemas e de aceite. Utilizando as principais ferramentas open source de testes do mercado (JUnit, Cucumber, RestAssured, Mockito, PITest e etc.).

## Build e Implantação
Utilize o terminal para fazer a implantação do projeto e poder consumir os serviços.

```bash
# Acessar a pasta root 
cd curso-automacao-aluno

# Responsável pelo build dos serviços
./build-all.sh

# Responsável por criar os containeres e disponibilizar no ambiente temporário (800X)
./start-fullstack-app.sh
```

Após o build e implantação é possível acessar os serviços para cada ambiente. Inicialmente disponibiliza-se acesso somente ao ambiente temporário. 

Ambiente Temporário: 
- Serviço de usuários e autenticação: 8000
- Serviço de clientes: 8001
- Serviço de produtos: 8002 
- Serviço de pedidos: 8003
- Aplicação Web: 8005

Ambiente de Desenvolvimento: 
- Serviço de usuários e autenticação: 8100
- Serviço de clientes: 8101
- Serviço de produtos: 8102 
- Serviço de pedidos: 8103
- Aplicação Web: 8105

Ambiente de Testes: 
- Serviço de usuários e autenticação: 8200
- Serviço de clientes: 8201
- Serviço de produtos: 8202 
- Serviço de pedidos: 8203
- Aplicação Web: 8205

Ambiente de Produção: 
- Serviço de usuários e autenticação: 8300
- Serviço de clientes: 8301
- Serviço de produtos: 8302 
- Serviço de pedidos: 8303
- Aplicação Web: 8305

> Utilizando o pipeline proposto no treinamento é possível 
> buildar e implantar os demais ambientes (dev, testes e prod).


> Esse projeto foi construído a título de aprendizagem, a maior parte dos conteúdos da página principal são estáticos e o sistema não possui algumas funções implementadas. Outrossim, bugs podem ser identificados devido a natureza educacional do projeto. 

##### Estrutura do Projeto
 
| Pasta | Descrição |
| ------ | ------ |
| customer-service | Microsserviço springboot para gestão de clientes. |
| jenkins | Para instalar o jenkins local via docker-compose. |
| order-service | Microsserviço springboot para gestão de pedidos. |
| product-service | Microsserviço springboot para gestão de produtos. |
| shutils | Pasta com arquivos de scripts para acelerar atividades de manutenção. |
| test-project-bdd-junit4-rest | Projeto para testes rest com Cucumber. |
| test-project-bdd-junit4-web | Projeto para testes web com Cucumber. |
| user-service | Microsserviço springboot para gestão de usuários.|
| web-app | Microsserviço web. |

## Links úteis
| Arquivo | Link |
| ------ | ------ |
| VDI (Virtual Box Image) do ambiente de treinamento | https://drive.google.com/file/d/1WzFSao4JyfMtel7rkk3Wz8PaCOANlTam/view?usp=sharing | 
| Canal no Youtube (Playlist com os vídeos de treinamento) | https://www.youtube.com/playlist?list=PLruAZvtLCirNN0jOoNcjbZfnI5hANoW4B |
| URL com a aplicação rodando em modo demo | https://curso-automacao-web-app.herokuapp.com/ |

## Instruções de Instalação
Para poder rodar o ambiente de treinamento é necessário ter o VirtualBox instalado e uma máquina com 16GB de RAM e 250GB SSD (com pelo menos 100GB livre). Para implantar a VDI no VirtualBox basta procurar vídeos de instrução no Youtube. 
Recomendação é utilizar na VM: 

> No mínimo 8GB de RAM;
> 50GB de disco livre;
> 4 núcleos do processador dedicados.

## License
Somente para finalidade educacional, é proibida a distribuição ou comercialização. Em caso de uso deve-se manter o reconhecimento ao autor do projeto. Sua utilização só é permitida após o consentimento expresso do autor. 

For educational purposes only. It's forbidden for commercial or distribution proposes. In case of reuse shall keep the reference of the author of the project. Your usage is only allowed after the expressed formal authorization from author. 
