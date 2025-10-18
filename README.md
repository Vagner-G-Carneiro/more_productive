# More Productive
## Motivo do Desenvolvimento
More Productive é uma aplicação FullStack, o intuíto dela é ser simples já que o foco de desenvolvimento não é um produto inovador, mas sim sintetizar conhecimento em várias areas da Técnologia, como Fron-End, Back-End, Banco de Dados, DevOp.<br><br>
More Productive é uma aplicação criada com a finalidade de testar e aprimorar meus conhecimentos, visando destaque no mercado de técnologia, que atualmente se mostra imensamente competitivo.<br><br>
A aplicação visa tornar simples e seguro a criação de tarefas e seus estados, mas utilizará algumas coisas bem legais na sua infraestrutura.<br><br>
Ela será um grande apanhado de tudo que sei.

## Basicamente, o que o programa fará e implementará:

### Front-End
Angular<br><br>
Escolhi Angular como Framework por ser o par romântico do JavaSpring e muito usado no mercado, optei por ele porque me parece interessante, simples de dar uma cara corporativa, mesmo tendo uma curva de aprendizado maior, me parece um bom começo para o Front.

### Back-End
Java Spring Para API<br><br>
Primeira escolha quando pensei nesse projeto, Java é bem consolidado no mercado, pode produzir sistemas escaláveis que lidam bem com milhares de usuários, não é atoa que domina a parte comporativa.
Spring<br>
Framwork que agiliza muito o java raiz, fora que é extremamente completo, gigante, temos projetos como SpringBoot, SpringSecurity, SpringCloud, por ser completo foi escolhido.

### Banco de Dados
Postgresql<br><br>
Simplesmente gosto muito, banco relacional, facíl de mexer e com o Servidor bem carismático, muito bom pgAdmin, fora que adoro Elefantes.<br><br>
Nada muito especial na escolha.

## Sobre o Projeto
A idéia é simples, usuário e usuário admin tem seus atributos o mais simples possível, o foco é como resolvemos os problemas que cada atributo traz<br>
Destasques para atributos senha e foto: onde senha será armazenado apenas seu hash, e foto será armazenado em Cloud.<br>
O usuário poderá trocar as credênciais de login mediante confirmação de email e senha atuais<br><br>

### Segurança<br>
A segurança da aplicação será feita pelo Spring Security, o foco final é que a To-Do list se enquadre até na Lei Geral De Proteção de Dados [LGPD]<br>
A aplicação contará com diferentes permissões [ROLES] para Usuário e Usuário Admin, e também será Stateless, usará Token para validações junto dos filtros do Spring Security<br>

### Docker
Também haverá uma imagem do projeto no DockerHub, o projeto terá um Dockerfile e um docker-compose para subir a aplicação com o banco junto.

### CI/CD
Usando GitHub Actions, a cada push automaticamente será rodado os teste unitários feitos com Junit e Mockito.

# Resumo de Tecnologias utilizadas
## Back End
- Java Spring
- Postgresql
- GitHub Actions
- Cloud -> A definir.

## Front End
- Angular

# Como executar o Projeto
- A definir explicação. 
Pré Requisitos: A definir.

# Autor/Desenvolvedor
Vagner Guimarães Carneiro

LinkedIn => https://www.linkedin.com/in/vagnergcarneiro
