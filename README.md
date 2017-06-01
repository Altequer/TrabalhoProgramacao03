# TrabalhoProgramacao03

Construir um programa para consultar o preço de CDs.
O programa deverá permitir ao usuário informar uma palavra chave de pesquisa e em seguida deverá procurar
CDs que tenham o nome do álbum ou o nome do artista igual à palavra chave digitada pelo usuário, nas lojas
on line.
O programa deverá exibir as seguintes informações como resposta à pesquisa: título do CD (álbum),
banda/artista, preço e loja. Os dados deverão ser exibidos em ordem crescente de valor. Um mesmo CD
poderá ser exibido mais de uma vez (considerando que mais de uma loja o tenha).
Porém, o usuário pode escolher reordenar os dados de apresentação, optando por:

    a) Ordem decrescente de valor
    b) Ordem alfabética pelo nome do álbum e crescente de valor
    c) Ordem alfabética pelo nome do artista/banda e decrescente de valor

O resultado de uma pesquisa pode ser salvo pelo usuário para posterior consulta, ou seja, em uma nova
execução o usuário pode consultar qualquer pesquisa que ele tenha salvo previamente. Para que ele consulte,
apresente a chave de pesquisa e a data/hora do salvamento da pesquisa.
Para uma pesquisa os dados devem ser buscados de duas fontes de dados: loja do Submarino e loja Som Livre.
Para obter os preços destas lojas foram disponibilizados duas bibliotecas Java: BrowseSubmarino.jar e
SomLivreCon.jar. Cada biblioteca possui uma interface de acesso diferente, conforme documentação JavaDoc
disponibilizado no AVA.

Os objetivos deste trabalho são:
  1) Utilizar o padrão de projetos Adapter para projetar uma interface única de pesquisa de preço de
  CDs nas lojas. Deverá ser construído um adaptador para cada loja. Os adaptadores deverão
  uniformizar a forma como se efetua pesquisa e como se obtém os dados da pesquisa efetuada por
  loja. Os adaptadores deverão seguir ao contrato estabelecido pela interface Loja.
    
    a. O método conectar será utilizado para efetuar a conexão com o servidor da loja.
    b. O método procurar será utilizado para efetuar uma busca no servidor da loja e filtrar por
    nome de banda ou título de CD. A busca somente pode ser feita se a conexão com o servidor foi bem sucedida.
    O método procurar deverá retornar uma coleção contendo a relação de produtos que
    atendem ao critério de pesquisa.
    Esta coleção não deverá conter mais de uma ocorrência do mesmo CD.
    c. O método desconectar será utilizado para desconectar do servidor da loja
    Cada um dos métodos da classe adaptadora deve delegar para as chamadas respectivas das classes
    adaptadas.

  2) Criar uma classe Façade para simplificar a busca por preços de CDs nas diversas lojas. O objetivo é
  simplesmente chamar o método pesquisar da fachada para que a consulta nas diversas lojas seja
  efetuada. A fachada deve se encarregar de criar uma nova coleção contendo a junção da coleção de
  CDs de todas as lojas.
  A fachada também oferece a funcionalidade de salvar e ler as pesquisas realizadas.

  3)Criar diagrama de classes da solução, cuja imagem deve estar no diretório de seu projeto Netbeans
  entregue.

  4) Criar interface gráfica para interagir com o usuário.
  Observações:
  Os componentes BrowseSubmarino.jar e SomLivreCon.jar não são reais, isto é, não se conectam de fato ao
  servidores das lojas. Estes componentes foram criados para fins didáticos. Com isso, tais componentes
  contém uma relação limitada de CDs, que é conhecida quando invocados os respectivos métodos.
  O usuário e senha para serem utilizados no componente SubmarinoProducts são “furb” e “furb”,
  respectivamente. Para o SomLivreServidor, informar qualquer sequência de caracteres.
  Trabalho para ser desenvolvido em duplas ou trios (não pode ser individual).
  Entrega do projeto Netbeans compactado, cujo nome do arquivo compactado é a concatenação dos
  nomes dos membros da equipe, através do link disponível no AVA em TrabFinal, até dia 14/06/2017
  às 18h30.
  Todas as equipes devem postar o trabalho até a data/hora indicada. Na aula deste dia, cada equipe deve
  apresentar seu trabalho ao professor (10 min), destacando os aspectos estudados este semestre:
  Persistência, Padrões de Projeto e JCF.
