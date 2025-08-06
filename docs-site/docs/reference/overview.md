---
sidebar_position: 1
---

# Visão Geral
:::note

É importante destacar que o projeto está no início de desenvolvimento e algumas das funcionalidades
que estão sendo desenvolvidas podem mudar.

:::

Esta API foi feita para atender as necessidades do contexto de **Empacotamento 3D**, e funciona
juntamente com o sistema visual do frontend, disponível [neste link](https://github.com/Empacotamento-Team/ants-at-work-frontend) 
do GitHub.

## Descrição
O sistema é feito em Java com ferramentas como Spring Boot, Spring Security, etc. O principal objetivo é
fornecer uma interface com o otimizador para gerar um plano de empacotamento.

As funcionalidades-chave se resumem a: autenticação; cadastro de produtos, embalagens e veículos; simulação
de empacotamentos com histórico, etc. _Todas ainda em desenvolvimento_.

## Autenticação e Autorização
O mecanismo de segurança atual funciona por meio de Bearer Tokens ou Api Key. Para ser possível usar endpoints 
com autenticação, é necessário usar o header de `Authorization` na requisição.
Dependendo do tipo de autorização escolhido, o valor deste cabeçalho seguirá os seguintes modelos: 
* Para Api Key: `ApiKey <chave de api>`
* Para Bearer Token: `Bearer <token>`

Para mais detalhes sobre autenticação e autorização, consulte o [contexto de identidade](identity).

## Requisições e Respostas
Como padrão de APIs Rest, a nossa aceita e responde em formato `application/json`. Caso ocorra erro nas requisições,
a API retornará o código de erro, a mensagem e o nome. Em caso de código 500, ou erro inesperado, entre em contato.
