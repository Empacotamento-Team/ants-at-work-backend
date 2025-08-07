import type {ReactNode} from 'react';
import clsx from 'clsx';
import Heading from '@theme/Heading';
import styles from './styles.module.css';

type FeatureItem = {
  title: string;
  Image: string;
  description: ReactNode;
  size?: string;
};

const FeatureList: FeatureItem[] = [
  {
    title: 'Empacotar!',
    Image: require('@site/static/img/box.png').default,
    description: (
      <>
        O Ants At Work está sendo projetado para otimizar a organização e a logística
        de pacotes nos caminhões.
      </>
    ),
    size: "150px",
  },
  {
    title: '"Caminhões também são gente"',
    Image: require('@site/static/img/truck.png').default,
    description: (
      <>
        Somos contra a discriminação e apoiamos o desenvolvimento de novos caminhões.
      </>
    ),
  },
  {
    title: 'Feito com Spring Boot',
    Image: require('@site/static/img/spring-logo.png').default,
    description: (
      <>
        Sistema desenvolvido com uma das melhores tecnologias para softwares backend, oferecendo
        segurança, praticidade, manutenção etc.
      </>
    ),
    size: "170px",
  },
];

function Feature({title, Image, description, size}: FeatureItem) {
  const divStyle: React.CSSProperties = {
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    height: "200px",
  };

  const imgStyle: React.CSSProperties = {
    width: size,
    height: size,
  };

  return (
    <div className={clsx('col col--4')}>
      <div className="text--center" style={divStyle}>
        <img className={styles.featureSvg} role="img" src={Image} alt={`${title} logo`}
             style={imgStyle}/>
      </div>
      <div className="text--center padding-horiz--md">
        <Heading as="h3">{title}</Heading>
        <p>{description}</p>
      </div>
    </div>
  );
}

export default function HomepageFeatures(): ReactNode {
  return (
    <section className={styles.features}>
      <div className="container">
        <div className="row">
          {FeatureList.map((props, idx) => (
            <Feature key={idx} {...props} />
          ))}
        </div>
      </div>
    </section>
  );
}
