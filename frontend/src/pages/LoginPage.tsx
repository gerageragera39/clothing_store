import { useState } from 'react';
import { Container, Paper, Title, TextInput, PasswordInput, Button, Text, Anchor, Group } from '@mantine/core';
import { useForm } from '@mantine/form';
import { useNavigate, Link } from 'react-router-dom';
import { notifications } from '@mantine/notifications';
import { authService } from '../services';

export function LoginPage() {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);

  const form = useForm({
    initialValues: {
      email: '',
      password: '',
    },
    validate: {
      email: (value) => (/^\S+@\S+$/.test(value) ? null : 'Invalid email'),
      password: (value) => (value.length >= 6 ? null : 'Password must be at least 6 characters'),
    },
  });

  const handleSubmit = async (values: { email: string; password: string }) => {
    setLoading(true);
    try {
      const response = await authService.login(values);
      localStorage.setItem('accessToken', response.accessToken);
      localStorage.setItem('refreshToken', response.refreshToken);
      
      notifications.show({
        title: 'Success',
        message: 'Welcome back!',
        color: 'green',
      });
      
      navigate('/catalog');
    } catch (error: any) {
      notifications.show({
        title: 'Error',
        message: error.response?.data?.message || 'Login failed',
        color: 'red',
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container size={420} my={40}>
      <Title ta="center" mb="md">
        Welcome back!
      </Title>

      <Paper withBorder shadow="md" p={30} radius="md">
        <form onSubmit={form.onSubmit(handleSubmit)}>
          <TextInput
            label="Email"
            placeholder="your@email.com"
            {...form.getInputProps('email')}
          />

          <PasswordInput
            label="Password"
            placeholder="Your password"
            mt="md"
            {...form.getInputProps('password')}
          />

          <Button fullWidth mt="xl" type="submit" loading={loading}>
            Sign in
          </Button>
        </form>

        <Group justify="center" mt="md">
          <Anchor component={Link} to="/register" size="sm">
            Don't have an account? Register
          </Anchor>
        </Group>
      </Paper>
    </Container>
  );
}
