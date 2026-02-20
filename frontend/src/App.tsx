import { Routes, Route, Navigate } from 'react-router-dom';
import { AppShell, Header, Group, Burger, ActionIcon, Container } from '@mantine/core';
import { useDisclosure } from '@mantine/hooks';
import { IconHome, IconShoppingCart, IconLogin, IconUserPlus, IconLogout } from '@tabler/icons-react';
import { HomePage } from './pages/HomePage';
import { CatalogPage } from './pages/CatalogPage';
import { LoginPage } from './pages/LoginPage';
import { RegisterPage } from './pages/RegisterPage';
import { CartPage } from './pages/CartPage';
import { notifications } from '@mantine/notifications';

export default function App() {
  const [mobileOpened, { toggle: toggleMobile }] = useDisclosure();
  const [desktopOpened, { toggle: toggleDesktop }] = useDisclosure(true);
  const isAuthenticated = !!localStorage.getItem('accessToken');

  const handleLogout = () => {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    notifications.show({
      title: 'Logged out',
      message: 'You have been successfully logged out',
      color: 'blue',
    });
  };

  return (
    <AppShell
      header={{ height: 60 }}
      navbar={{ width: 200, breakpoint: 'sm', collapsed: { mobile: !mobileOpened } }}
      padding="md"
    >
      <AppShell.Header>
        <Group h="100%" px="md" justify="space-between">
          <Group>
            <Burger opened={mobileOpened} onClick={toggleMobile} hiddenFrom="sm" size="sm" />
            <Burger opened={desktopOpened} onClick={toggleDesktop} visibleFrom="sm" size="sm" />
            <Group>
              <ActionIcon component="a" href="/" variant="subtle">
                <IconHome size={20} />
              </ActionIcon>
              <ActionIcon component="a" href="/catalog" variant="subtle">
                Catalog
              </ActionIcon>
            </Group>
          </Group>

          <Group>
            <ActionIcon component="a" href="/cart" variant="subtle">
              <IconShoppingCart size={20} />
            </ActionIcon>
            
            {isAuthenticated ? (
              <ActionIcon onClick={handleLogout} variant="subtle" color="red">
                <IconLogout size={20} />
              </ActionIcon>
            ) : (
              <>
                <ActionIcon component="a" href="/login" variant="subtle">
                  <IconLogin size={20} />
                </ActionIcon>
                <ActionIcon component="a" href="/register" variant="subtle">
                  <IconUserPlus size={20} />
                </ActionIcon>
              </>
            )}
          </Group>
        </Group>
      </AppShell.Header>

      <AppShell.Main>
        <Container fluid>
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/catalog" element={<CatalogPage />} />
            <Route path="/login" element={isAuthenticated ? <Navigate to="/catalog" /> : <LoginPage />} />
            <Route path="/register" element={isAuthenticated ? <Navigate to="/catalog" /> : <RegisterPage />} />
            <Route path="/cart" element={<CartPage />} />
          </Routes>
        </Container>
      </AppShell.Main>
    </AppShell>
  );
}
